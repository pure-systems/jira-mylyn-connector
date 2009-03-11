/*******************************************************************************
 * Copyright (c) 2009 Atlassian and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Atlassian - initial API and implementation
 ******************************************************************************/

package com.atlassian.connector.eclipse.internal.bamboo.ui.operations;

import com.atlassian.connector.eclipse.internal.bamboo.core.BambooCorePlugin;
import com.atlassian.connector.eclipse.internal.bamboo.core.TestResultExternalizer;
import com.atlassian.connector.eclipse.internal.bamboo.core.client.BambooClient;
import com.atlassian.connector.eclipse.internal.bamboo.ui.BambooUiPlugin;
import com.atlassian.theplugin.commons.bamboo.BambooBuild;
import com.atlassian.theplugin.commons.bamboo.BuildDetails;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.mylyn.commons.core.StatusHandler;
import org.eclipse.mylyn.tasks.core.TaskRepository;

import java.io.File;
import java.io.IOException;

public class RetrieveTestResultsJob extends Job {
	private final BambooBuild build;

	private final TaskRepository repository;

	private File testResults;

	public RetrieveTestResultsJob(BambooBuild build, TaskRepository repository) {
		super("Retrieve build log");
		this.build = build;
		this.repository = repository;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		BambooClient client = BambooCorePlugin.getRepositoryConnector().getClientManager().getClient(repository);
		try {
			BuildDetails details = client.getBuildDetails(monitor, repository, build);
			TestResultExternalizer tre = new TestResultExternalizer();
			testResults = tre.writeApplicationsToXML(build, details, File.createTempFile("bamboo_result", ".xml"));
		} catch (CoreException e) {
			StatusHandler.log(new Status(IStatus.ERROR, BambooUiPlugin.PLUGIN_ID,
					"Failed to retrieve build logs for build " + build.getPlanKey(), e));
		} catch (IOException e) {
			StatusHandler.log(new Status(IStatus.ERROR, BambooUiPlugin.PLUGIN_ID,
					"Failed to process test results for build " + build.getPlanKey(), e));
		}
		return Status.OK_STATUS;
	}

	public File getTestResultsFile() {
		return testResults;
	}
}