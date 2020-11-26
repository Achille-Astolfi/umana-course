package component.configuration;

import java.util.Iterator;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import iface.service.MvcUsersService;
import model.domain.ConstraintViolationData;
import model.entity.MvcUser;;

@Configuration
@EnableWebMvc
public class UmanaWebMvcConfigurer implements WebMvcConfigurer {
	private Logger logger = LoggerFactory.getLogger(MvcUsersService.class);

	@Autowired
	private MvcUsersService mvcUsersService;

	@PostConstruct
	private void initConfiguration() {
		logger.info("===============================================");
		mvcUsersService.createMvcUser("user", "password");
		mvcUsersService.createMvcUser("admin", "password");
		logger.info("===============================================");
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addWebRequestInterceptor(mvcUserInterceptor());
	}

	@Bean
	MvcUserInterceptor mvcUserInterceptor() {
		return new MvcUserInterceptor();
	}

	@ControllerAdvice
	@Component
	static class UmanaResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

		@ExceptionHandler(UnauthorizedException.class)
		ResponseEntity<?> handleUnauthorized(UnauthorizedException ue) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)//
					.header("WWW-Authenticate", "Basic realm=\"Umana\"")//
					.build();
		}

		@ExceptionHandler(ConstraintViolationException.class)
		ResponseEntity<?> handleValidation(ConstraintViolationException cve) {
			return ResponseEntity.badRequest()
					.body(Resources.wrap(IterableWrapper.wrap(cve.getConstraintViolations())));
		}
	}

	static class IterableWrapper implements Iterable<ConstraintViolationData> {
		static IterableWrapper wrap(Iterable<ConstraintViolation<?>> set) {
			return new IterableWrapper(set.iterator());
		}

		private final Iterator<ConstraintViolation<?>> iterator;

		public IterableWrapper(Iterator<ConstraintViolation<?>> iterator) {
			this.iterator = iterator;
		}

		@Override
		public Iterator<ConstraintViolationData> iterator() {
			return new Iterator<ConstraintViolationData>() {

				@Override
				public boolean hasNext() {
					return iterator.hasNext();
				}

				@Override
				public ConstraintViolationData next() {
					return new ConstraintViolationData(iterator.next());
				}

			};
		}

	}

	static class MvcUserInterceptor implements WebRequestInterceptor {
		private Logger logger = LoggerFactory.getLogger(MvcUserInterceptor.class);

		@Autowired
		private MvcUsersService mvcUsersService;

		@Override
		public void preHandle(WebRequest request) throws Exception {
			var auth = request.getHeader("Authorization");
			logger.debug(auth);
			auth = strip(auth, "Basic ");
			try {
				Optional<MvcUser> mvcUser;
				if (auth != null && !auth.isBlank()
						&& (mvcUser = mvcUsersService.readMvcUserByAuth(auth)).isPresent()) {
					request.setAttribute("com.example.user", mvcUser.get(), WebRequest.SCOPE_REQUEST);
					return;
				}
			} catch (PersistenceException pe) {
				// fallback
			}
			throw new UnauthorizedException();
		}

		private String strip(String s, String prefix) {
			if (s != null && s.startsWith(prefix)) {
				return s.substring(prefix.length());
			} else {
				return null;
			}
		}

		@Override
		public void postHandle(WebRequest request, ModelMap model) throws Exception {
			// TODO Auto-generated method stub

		}

		@Override
		public void afterCompletion(WebRequest request, Exception ex) throws Exception {
			// TODO Auto-generated method stub

		}

	}
}
