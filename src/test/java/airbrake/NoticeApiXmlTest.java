// Modified or written by Luca Marrocco for inclusion with airbrake.
// Copyright (c) 2009 Luca Marrocco.
// Licensed under the Apache License, Version 2.0 (the "License")

package airbrake;

import ch.qos.logback.classic.spi.ThrowableProxy;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

public class NoticeApiXmlTest {

	private AirbrakeNotice notice;

	private String clean(String string) {
		return string.replaceAll("\\\"", "");
	}

	@Before
	public void setUp() {
		notice = new AirbrakeNoticeBuilder("apiKey", new ThrowableProxy(new RuntimeException("errorMessage"))   ).newNotice();
	}

	@Test
	public void testApiKey() {
		assertThat(xml(new NoticeXml(notice)), containsString("<api-key>apiKey</api-key>"));
	}

	@Test
	public void testError() {
		assertThat(xml(new NoticeXml(notice)), containsString("error>"));
	}

	@Test
	public void testErrorBacktrace() {
		assertThat(xml(new NoticeXml(notice)), containsString("backtrace>"));
	}

	@Test
	public void testErrorBacktraceLine() {
		assertThat(xml(new NoticeXml(notice)), containsString("<line method=org.junit.runners.BlockJUnit4ClassRunner.runChild file=BlockJUnit4ClassRunner.java number="));
	}

	@Test
	public void testErrorClass() {
		assertThat(xml(new NoticeXml(notice)), containsString("<class><![CDATA[java.lang.RuntimeException]]></class>"));
	}

	@Test
	public void testErrorMessage() {
		assertThat(xml(new NoticeXml(notice)), containsString("<message><![CDATA[errorMessage]]></message>"));
	}

	@Test
	public void testNoticeVersion() {
		assertThat(xml(new NoticeXml(notice)), containsString("notice version=2.2"));
	}

	@Test
	public void testNotifier() {
		assertThat(xml(new NoticeXml(notice)), containsString("notifier>"));
	}

	@Test
	public void testNotifierName() {
		assertThat(xml(new NoticeXml(notice)), containsString("<name><![CDATA[airbrake-java]]></name>"));
	}

	@Test
	public void testNotifierUrl() {
		assertThat(xml(new NoticeXml(notice)), containsString("<url><![CDATA[https://github.com/airbrake/airbrake-java]]></url>"));
	}

	@Test
	public void testNotifierVersion() {
		assertThat(xml(new NoticeXml(notice)), containsString("<version><![CDATA[2.2]]></version>"));
	}

	private String xml(NoticeXml noticeApi) {
		return clean(noticeApi.toString());
	}

}
