/**
 * 
 */
package com.bridgeit.apigateway.filter;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgeit.apigateway.redisrepository.IRedisRepository;
import com.bridgeit.apigateway.tokengenerator.TokenGenerator;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

/**
 * 
 * 
 * @author Chaithra-Shenoy
 * @since Date 10-07-2018 <br>
 *        <p>
 *        <b>Route filter class </b>
 *        </p>
 */
@Service
public class RouteFilter extends ZuulFilter {

	private static final Logger LOGGER = LoggerFactory.getLogger(RouteFilter.class);

	
	@Autowired
	private IRedisRepository iRedisRepository;

	@Override
	public boolean shouldFilter() {

		return true;
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

	@Override
	public Object run() {
		System.out.println("In zuul filter");
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		if(request.getRequestURI().startsWith("/zuulnote/note/")) {
			String tokenFromHeader = request.getHeader("token");
			System.out.println(tokenFromHeader);
			
			if(tokenFromHeader==null) {
				
					try {
						throw new Exception("No Token Found in header");
					} catch (Exception e) {
						e.printStackTrace();
					}
			}
			
			TokenGenerator tokenProvider = new TokenGenerator();
			String userId = tokenProvider.parseJwt(tokenFromHeader).getId();
			System.out.println(userId);
			String tokenFromRedis = iRedisRepository.getToken(userId);
			if (tokenFromRedis == null) {
				return null;
			} else {
				ctx.addZuulRequestHeader("userId", userId);
				request.setAttribute("userId", userId);
				LOGGER.info("RouteFilter: "
						+ String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));
			}
		}
		return null;
	}
}
