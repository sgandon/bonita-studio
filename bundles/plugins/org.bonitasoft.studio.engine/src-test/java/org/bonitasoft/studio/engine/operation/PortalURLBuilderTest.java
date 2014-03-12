/**
 * Copyright (C) 2014 BonitaSoft S.A.
 * BonitaSoft, 32 rue Gustave Eiffel - 38000 Grenoble
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2.0 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.bonitasoft.studio.engine.operation;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

import java.net.URL;
import java.net.URLEncoder;

import org.bonitasoft.studio.common.repository.Repository;
import org.bonitasoft.studio.model.configuration.Configuration;
import org.bonitasoft.studio.model.configuration.ConfigurationFactory;
import org.bonitasoft.studio.model.process.AbstractProcess;
import org.bonitasoft.studio.model.process.ProcessFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Romain Bioteau
 *
 */
public class PortalURLBuilderTest {

	private PortalURLBuilder portalURLBuilder;
	private String loginURL;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		AbstractProcess process = ProcessFactory.eINSTANCE.createPool();
		process.setName("testPool");
		process.setVersion("1.0");
		Configuration configuration = ConfigurationFactory.eINSTANCE.createConfiguration();
		portalURLBuilder = spy(new PortalURLBuilder());
		doReturn("fr").when(portalURLBuilder).getWebLocale();
		doReturn("william.jobs").when(portalURLBuilder).getDefaultUsername();
		doReturn("bpm").when(portalURLBuilder).getDefaultPassword();
		doReturn(configuration).when(portalURLBuilder).getConfiguration();
		loginURL = "http://fakeLoginURL";
		doReturn(loginURL).when(portalURLBuilder).buildLoginUrl(anyString(),anyString());
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link org.bonitasoft.studio.engine.operation.ApplicationURLBuilder#toURL(org.eclipse.core.runtime.IProgressMonitor)}.
	 */
	@Test
	public void shouldToURL_RetursAValidURL() throws Exception {
		URL url = portalURLBuilder.toURL(Repository.NULL_PROGRESS_MONITOR);
		assertThat(url).isNotNull();
		String validApplicationPath = URLEncoder.encode("portal/homepage","UTF-8");

		String validEntryParameters = URLEncoder.encode("#?_p=tasklistinguser&_pf=1&_f=available","UTF-8");
		String validLocale = URLEncoder.encode("_l=fr","UTF-8");
		assertThat(url.toString()).contains(validApplicationPath).endsWith(validEntryParameters).contains(validLocale).startsWith(loginURL);
	}

}