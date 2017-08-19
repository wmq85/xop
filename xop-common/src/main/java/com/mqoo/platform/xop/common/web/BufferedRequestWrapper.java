package com.mqoo.platform.xop.common.web;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * HttpServletRequest Wrapper 
 * <p>
 * HttpServletRequest包装类
 *
 */
public class BufferedRequestWrapper extends HttpServletRequestWrapper {

	private ByteArrayInputStream bais = null;
	private ByteArrayOutputStream baos = null;
	private BufferedServletInputStream bsis = null;
	private byte[] buffer = null;

	public BufferedRequestWrapper(HttpServletRequest req) throws IOException {
		super(req);
		// Read InputStream and store its content in a buffer.
		InputStream is = req.getInputStream();
		this.baos = new ByteArrayOutputStream();
		byte buf[] = new byte[1024];
		int read;
		while ((read = is.read(buf)) > 0) {
			this.baos.write(buf, 0, read);
		}
		this.buffer = this.baos.toByteArray();
	}

	@Override
	public ServletInputStream getInputStream() {
		this.bais = new ByteArrayInputStream(this.buffer);
		this.bsis = new BufferedServletInputStream(this.bais);
		return this.bsis;
	}

	public String getRequestBody() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(this.getInputStream()));
		String line = null;
		StringBuilder inputBuffer = new StringBuilder();
		do {
			line = reader.readLine();
			if (null != line) {
				inputBuffer.append(line.trim());
			}
		} while (line != null);
		reader.close();
		return inputBuffer.toString().trim();
	}

}

class BufferedServletInputStream extends ServletInputStream {

	private ByteArrayInputStream bais;

	public BufferedServletInputStream(ByteArrayInputStream bais) {
		this.bais = bais;
	}

	@Override
	public int available() {
		return this.bais.available();
	}

	@Override
	public int read() {
		return this.bais.read();
	}

	@Override
	public int read(byte[] buf, int off, int len) {
		return this.bais.read(buf, off, len);
	}

	@Override
	public boolean isFinished() {
		return false;
	}

	@Override
	public boolean isReady() {
		return true;
	}

	@Override
	public void setReadListener(ReadListener readListener) {

	}
}
