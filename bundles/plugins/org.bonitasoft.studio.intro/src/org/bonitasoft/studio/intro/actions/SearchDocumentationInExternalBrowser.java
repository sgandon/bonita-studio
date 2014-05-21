/**
 * Copyright (C) 2014 Bonitasoft S.A.
 * Bonitasoft, 32 rue Gustave Eiffel - 38000 Grenoble
 *
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
package org.bonitasoft.studio.intro.actions;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Properties;

import org.bonitasoft.studio.common.log.BonitaStudioLog;
import org.bonitasoft.studio.engine.command.OpenBrowserCommand;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.intro.IIntroSite;
import org.eclipse.ui.intro.config.IIntroAction;

/**
 * @author Aurelien Pupier
 *
 */
public class SearchDocumentationInExternalBrowser implements IIntroAction {
	
	private final static String SEARCH_DOC_URL = "http://documentation.bonitasoft.com/search/site/";
	
	@Override
	public void run(IIntroSite arg0, Properties params) {
		String urlToOpen = calculateURLToOpen(params);
		openInExternalBrowser(urlToOpen);
	}

	private void openInExternalBrowser(String urlToOpen) {
		try {
			URL url = new URL(urlToOpen);
			new OpenBrowserCommand(url, "", "").execute(null);
		} catch (MalformedURLException e) {
			BonitaStudioLog.error(e);
		} catch (ExecutionException e) {
			BonitaStudioLog.error(e);
		}
	}

	private String calculateURLToOpen(Properties params) {	
		String searchField = "";
		try {
			searchField = URLEncoder.encode(params.getProperty("keys"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			BonitaStudioLog.error(e);
		}
		return SEARCH_DOC_URL + searchField;
	}

}
