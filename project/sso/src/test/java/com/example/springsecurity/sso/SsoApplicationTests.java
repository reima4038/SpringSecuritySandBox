package com.example.springsecurity.sso;

import jdk.jfr.Description;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import sun.rmi.runtime.Log;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SsoApplicationTests {
	final String REQUEST_HEADER_USER_ID = "x-uid";
	final String REQUEST_HEADER_PASSWORD = "x-pwd";
	final String PAGE_PATH_TOP = "/top";
	final String PAGE_PATH_ADMIN = "/admin";

	@Autowired
	private MockMvc mockMvc;

	@Test
	void RequestHeaderの認証情報を使って認証しトップページに遷移する() throws Exception {
		final String userId = "T10000000";
		final String password = "";

		final RequestBuilder request = get(PAGE_PATH_TOP)
				.header(REQUEST_HEADER_USER_ID, userId)
				.header(REQUEST_HEADER_PASSWORD, password);

		final String expectBodyText = "トップページ（認証要）";
		mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().is2xxSuccessful())
				.andExpect(content().string(containsString(expectBodyText)));

	}

	@Test
	void 管理権限をもつユーザは管理画面へアクセスできる() throws Exception {
		final String userId = "T10000000";
		final String password = "";

		final RequestBuilder request = get(PAGE_PATH_ADMIN)
				.header(REQUEST_HEADER_USER_ID, userId)
				.header(REQUEST_HEADER_PASSWORD, password);

		final String expectBodyText = "トップページ（認証要:管理者ユーザ専用）";
		mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().is2xxSuccessful())
				.andExpect(content().string(containsString(expectBodyText)));

	}

	@Test
	void 管理権限をもたないユーザは管理画面へのアクセスを403Forbiddenで拒否される() throws Exception {
		final String userId = "T10000001";
		final String password = "";

		final RequestBuilder request = get(PAGE_PATH_ADMIN)
				.header(REQUEST_HEADER_USER_ID, userId)
				.header(REQUEST_HEADER_PASSWORD, password);

		mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isForbidden());
	}

}
