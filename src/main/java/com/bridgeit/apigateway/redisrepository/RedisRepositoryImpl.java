/**
 * 
 */
package com.bridgeit.apigateway.redisrepository;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.bridgeit.apigateway.tokengenerator.TokenGenerator;


/**
 * 
 * 
 * @author Chaithra-Shenoy
 * @since Date 10-07-2018 <br>
 *        <p>
 *        <b>RedisRepository implementation class</b>
 *        </p>
 */
@Repository
public class RedisRepositoryImpl implements IRedisRepository {

	@Autowired
	TokenGenerator tokenGen=new TokenGenerator();

	private RedisTemplate<String, Object> redisTemplate;
	private static HashOperations<String, String, String> hashOperations;
	private static String KEY = "chaithra";

	@Autowired
	public RedisRepositoryImpl(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	/**
	 * 
	 */
	public RedisRepositoryImpl() {
		// TODO Auto-generated constructor stub
	}

	private static Logger logger = LoggerFactory.getLogger(RedisRepositoryImpl.class);

	/**
	 * To initialize hash operations and this method MUST be invoked before the class is put into service.
	 */
	@PostConstruct
	private void init() {
		hashOperations = redisTemplate.opsForHash();
	}

	/**
	 * @param clientId
	 * @param jwtToken
	 */
	@Override
	public void setToken(String jwtToken) {
		String userId = tokenGen.parseJwt(jwtToken).getId();
		hashOperations.put(KEY, userId, jwtToken);
		logger.info("Token set in redis");
	}

	/**
	 * @param clientId
	 * @return token
	 */
	@Override
	public String getToken(String userId) {
		logger.info("Getting token from redis");
		return hashOperations.get(KEY, userId);
	}

	/**
	 * @param clientId
	 */
	@Override
	public void deleteToken(String userId) {
		logger.info("Deleting token from redis");
		hashOperations.delete(KEY, userId);
	}
}
