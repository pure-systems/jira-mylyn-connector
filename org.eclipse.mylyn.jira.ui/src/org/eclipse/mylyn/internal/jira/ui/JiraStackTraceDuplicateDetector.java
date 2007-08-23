/*******************************************************************************
 * Copyright (c) 2004, 2007 Mylyn project committers and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.eclipse.mylyn.internal.jira.ui;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.mylyn.internal.jira.core.model.filter.ContentFilter;
import org.eclipse.mylyn.internal.jira.core.model.filter.FilterDefinition;
import org.eclipse.mylyn.tasks.core.RepositoryTaskData;
import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.mylyn.tasks.ui.AbstractDuplicateDetector;
import org.eclipse.mylyn.tasks.ui.TaskFactory;
import org.eclipse.mylyn.tasks.ui.TasksUiPlugin;
import org.eclipse.mylyn.tasks.ui.editors.AbstractNewRepositoryTaskEditor;
import org.eclipse.mylyn.tasks.ui.search.SearchHitCollector;

/**
 * Stack Trace duplicate detector
 * 
 * @author Eugene Kuleshov
 */
public class JiraStackTraceDuplicateDetector extends AbstractDuplicateDetector {

	private static final String NO_STACK_MESSAGE = "Unable to locate a stack trace in the description text.";

	public SearchHitCollector getSearchHitCollector(TaskRepository repository, RepositoryTaskData taskData) {
		String searchString = AbstractNewRepositoryTaskEditor.getStackTraceFromDescription(taskData.getDescription());
		if (searchString == null) {
			MessageDialog.openWarning(null, "No Stack Trace Found", NO_STACK_MESSAGE);
			return null;
		}

		try {
			String encoding = repository.getCharacterEncoding();

			FilterDefinition filter = new FilterDefinition();
			// this shouldn't be needed, but RssFilterConverter need serious face lift
			filter.setContentFilter(new ContentFilter(URLEncoder.encode(searchString, encoding), //
					false, true, false, true));

			JiraCustomQuery query = new JiraCustomQuery(repository.getUrl(), filter, encoding);

			return new SearchHitCollector(TasksUiPlugin.getTaskListManager().getTaskList(), repository, query,
					new TaskFactory(repository, false, false));
		} catch (UnsupportedEncodingException ex) {
			return null;
		}
	}

}