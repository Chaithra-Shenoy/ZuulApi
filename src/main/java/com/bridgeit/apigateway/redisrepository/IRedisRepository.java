/**
 * 
 */
package com.bridgeit.apigateway.redisrepository;

import org.springframework.stereotype.Repository;

/**
 * 
 * 
 * @author Chaithra-Shenoy
 * @since Date 10-07-2018 <br>
 *        <p>
 *        <b>IRedisRepository interface having three abstract methods </b>
 *        </p>
 */

public interface IRedisRepository {

	/**
	 * @param token
	 */
	void setToken(String token);
	/**
	 * @param userId
	 * @return
	 */
	public String getToken(String userId);
	/**
	 * @param userId
	 */
	public void deleteToken(String userId) ;
}
