package mx.com.endtoend.infrastructure.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter{

	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtAccessTokenConverter jwtAccessTokenConverter;
	
	@Autowired
	JwtTokenStore jwtTokenStore;
	
	@Autowired
	InfoAdditionalToken infoAdditionalToken;
	
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients
		.inMemory().withClient("posOnlineE2E")
		.secret(bCryptPasswordEncoder.encode("etePosOnline"))
		.scopes("read","write")
		.authorizedGrantTypes("password","refresh_token")
		.accessTokenValiditySeconds(3600 * 16)
		.refreshTokenValiditySeconds(3600 * 16);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		TokenEnhancerChain tokenEnhancer = new TokenEnhancerChain();
		tokenEnhancer.setTokenEnhancers(Arrays.asList(jwtAccessTokenConverter,infoAdditionalToken));
		
		endpoints.pathMapping("/oauth/token", "/api/security/access/oauth/token")
		.authenticationManager(authenticationManager).tokenStore(jwtTokenStore).reuseRefreshTokens(false)
		.accessTokenConverter(jwtAccessTokenConverter);
		
		endpoints.tokenStore(jwtTokenStore).tokenEnhancer(tokenEnhancer);
	}
}
