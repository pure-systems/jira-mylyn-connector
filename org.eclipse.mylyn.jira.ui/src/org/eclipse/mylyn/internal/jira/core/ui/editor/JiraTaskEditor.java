/*******************************************************************************
 * Copyright (c) 2004 - 2006 Mylar committers and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.eclipse.mylar.internal.jira.core.ui.editor;

import java.util.Arrays;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.mylar.internal.jira.core.JiraAttributeFactory;
import org.eclipse.mylar.tasks.core.ITask;
import org.eclipse.mylar.tasks.core.RepositoryTaskAttribute;
import org.eclipse.mylar.tasks.core.RepositoryTaskData;
import org.eclipse.mylar.tasks.ui.editors.AbstractRepositoryTaskEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * @author Mik Kersten
 * @author Rob Elves
 */
public class JiraTaskEditor extends AbstractRepositoryTaskEditor {

	//private static final String SUBMIT_JOB_LABEL = "Submitting to JIRA repository";

	private RepositoryTaskData taskData;

	public JiraTaskEditor(FormEditor editor) {
		super(editor);
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) {
		super.init(site, input);
//		if (!(input instanceof RepositoryTaskEditorInput)) {
//			return;
//		}
//		super.init(site, input);
//
//		editorInput = (AbstractTaskEditorInput) input;
//		taskData = editorInput.getTaskData();
//		repository = editorInput.getRepository();
//		connector = (JiraRepositoryConnector) TasksUiPlugin.getRepositoryManager().getRepositoryConnector(
//				repository.getKind());
//
//		setSite(site);
//		setInput(input);
//
//		taskOutlineModel = RepositoryTaskOutlineNode.parseBugReport(editorInput.getTaskData());
//		
//		isDirty = false;
//		updateEditorTitle();
	}

	@Override
	protected void addAttachContextButton(Composite buttonComposite, ITask task) {
		// disabled
	}

	@Override
	protected void addSelfToCC(Composite composite) {
		// disabled
	}
	
	@Override
	protected void addCCList(Composite attributesComposite) {
		// disabled
	}
	
	@Override
	protected void createCustomAttributeLayout(Composite composite) {
		FormToolkit toolkit = getManagedForm().getToolkit();
		Label label = toolkit.createLabel(composite, "Components:");
		GridDataFactory.fillDefaults().align(SWT.RIGHT, SWT.CENTER).applyTo(label);

		final List componentsList = new List(composite, SWT.MULTI | SWT.V_SCROLL);
		componentsList.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
		componentsList.setFont(TEXT_FONT);
		GridData compTextData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		compTextData.horizontalSpan = 1;
		compTextData.widthHint = 125;
		compTextData.heightHint = 40;
		componentsList.setLayoutData(compTextData);
		taskData = getRepositoryTaskData();
		RepositoryTaskAttribute attribute = taskData.getAttribute(JiraAttributeFactory.ATTRIBUTE_COMPONENTS);
		if (attribute != null && !attribute.getOptions().isEmpty()) {
			componentsList.setItems(attribute.getOptions().toArray(new String[1]));
			for (String compStr : taskData.getAttributeValues(JiraAttributeFactory.ATTRIBUTE_COMPONENTS)) {
				componentsList.select(componentsList.indexOf(compStr));
			}
			componentsList.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					RepositoryTaskAttribute attribute = taskData
							.getAttribute(JiraAttributeFactory.ATTRIBUTE_COMPONENTS);
					attribute.clearValues();
					attribute.setValues(Arrays.asList(componentsList.getSelection()));
					markDirty(true);
					
				}
			});
		}

		label = toolkit.createLabel(composite, "Fix Versions:");
		GridDataFactory.fillDefaults().align(SWT.RIGHT, SWT.CENTER).applyTo(label);
		final List versionsList = new List(composite, SWT.MULTI | SWT.V_SCROLL);
		versionsList.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
		versionsList.setFont(TEXT_FONT);
		GridData versionsTextData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		versionsTextData.horizontalSpan = 1;
		versionsTextData.widthHint = 125;
		versionsTextData.heightHint = 40;
		versionsList.setLayoutData(versionsTextData);
		attribute = taskData.getAttribute(JiraAttributeFactory.ATTRIBUTE_FIXVERSIONS);
		if (attribute != null && !attribute.getOptions().isEmpty()) {
			versionsList.setItems(attribute.getOptions().toArray(new String[1]));
			for (String versionStr : taskData.getAttributeValues(JiraAttributeFactory.ATTRIBUTE_FIXVERSIONS)) {
				versionsList.select(versionsList.indexOf(versionStr));
			}
			versionsList.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					RepositoryTaskAttribute attribute = taskData
							.getAttribute(JiraAttributeFactory.ATTRIBUTE_FIXVERSIONS);
					attribute.clearValues();
					attribute.setValues(Arrays.asList(versionsList.getSelection()));
					markDirty(true);
				}
			});
		}

		label = toolkit.createLabel(composite, "Affects Versions:");
		GridDataFactory.fillDefaults().align(SWT.RIGHT, SWT.CENTER).applyTo(label);
		final List affectsVersionsList = new List(composite, SWT.MULTI | SWT.V_SCROLL);
		affectsVersionsList.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
		affectsVersionsList.setFont(TEXT_FONT);
		GridData affectsVersionsTextData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		affectsVersionsTextData.horizontalSpan = 1;
		affectsVersionsTextData.widthHint = 125;
		affectsVersionsTextData.heightHint = 40;
		affectsVersionsTextData.verticalIndent = 3;
		affectsVersionsList.setLayoutData(affectsVersionsTextData);
		attribute = taskData.getAttribute(JiraAttributeFactory.ATTRIBUTE_AFFECTSVERSIONS);
		if (attribute != null && !attribute.getOptions().isEmpty()) {
			affectsVersionsList.setItems(attribute.getOptions().toArray(new String[1]));
			for (String versionStr : taskData.getAttributeValues(JiraAttributeFactory.ATTRIBUTE_AFFECTSVERSIONS)) {
				affectsVersionsList.select(affectsVersionsList.indexOf(versionStr));
			}
			affectsVersionsList.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					RepositoryTaskAttribute attribute = taskData
							.getAttribute(JiraAttributeFactory.ATTRIBUTE_AFFECTSVERSIONS);
					attribute.clearValues();
					attribute.setValues(Arrays.asList(affectsVersionsList.getSelection()));
					markDirty(true);
				}
			});
		}
	}

//	@Override
//	public void submitToRepository() {
//		if (isDirty()) {
//			this.doSave(new NullProgressMonitor());
//		}
//		updateTask();
//		submitButton.setEnabled(false);
//		showBusy(true);
//
//		final JiraServer jiraServer = JiraServerFacade.getDefault().getJiraServer(repository);
//		if (jiraServer == null) {
//			submitButton.setEnabled(true);
//			JiraTaskEditor.this.showBusy(false);
//			return;
//		}
//
////		// TODO: build a new issue object rather then retrieving from server
////		final Issue issue = jiraServer.getIssue(this.getRepositoryTaskData().getAttributeValue(
////				JiraAttributeFactory.ATTRIBUTE_ISSUE_KEY));
////		if (issue == null) {
////			MylarStatusHandler.log("Unable to retrieve issue from repository", this);
////			submitButton.setEnabled(true);
////			JiraTaskEditor.this.showBusy(false);
////			return;
////		}
//
////		final String comment = getNewCommentText();
//		final AbstractRepositoryTask task = (AbstractRepositoryTask) TasksUiPlugin.getTaskListManager().getTaskList()
//				.getTask(AbstractRepositoryTask.getHandle(repository.getUrl(), getRepositoryTaskData().getId()));
//		final boolean attachContext = false;
//
//		JobChangeAdapter listener = new JobChangeAdapter() {
//			@Override
//			public void done(final IJobChangeEvent event) {
//				PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
//					public void run() {
//						if (event.getJob().getResult().isOK()) {
//							if (attachContext) {
//								// TODO check for task == null
//								// TODO should be done as part of job
//								try {
//									connector.attachContext(repository, task, "");
//								} catch (Exception e) {
//									// TODO Auto-generated catch block
//									e.printStackTrace();
//								}
//							}
//							//close();
//						} else {
//							submitButton.setEnabled(true);
//							JiraTaskEditor.this.showBusy(false);
//						}
//					}
//				});
//			}
//		};
//
//		Job submitJob = new Job(SUBMIT_JOB_LABEL) {
//
//			@Override
//			protected IStatus run(IProgressMonitor monitor) {
//				try {
//					Issue issue = JiraRepositoryConnector.buildJiraIssue(getRepositoryTaskData(), jiraServer);
//
//					RepositoryOperation operation = getRepositoryTaskData().getSelectedOperation();
//					if (operation != null) {
//						if ("leave".equals(operation.getKnobName())) {
//							jiraServer.updateIssue(issue, comment);
//						} else if (org.tigris.jira.core.model.Status.RESOLVED_ID.equals(operation.getKnobName())) {
//							String value = operation.getOptionValue(operation.getOptionSelection());
//							jiraServer.resolveIssue(issue, jiraServer.getResolutionById(value), issue.getFixVersions(),
//									comment, JiraServer.ASSIGNEE_CURRENT, repository.getUserName());
//						} else if (org.tigris.jira.core.model.Status.OPEN_ID.equals(operation.getKnobName())) {
//							jiraServer.reopenIssue(issue, comment, JiraServer.ASSIGNEE_CURRENT, repository
//									.getUserName());
//						} else if (org.tigris.jira.core.model.Status.CLOSED_ID.equals(operation.getKnobName())) {
//							String value = operation.getOptionValue(operation.getOptionSelection());
//							jiraServer.closeIssue(issue, jiraServer.getResolutionById(value), issue.getFixVersions(),
//									comment, JiraServer.ASSIGNEE_CURRENT, repository.getUserName());
//						}
//
//					}
//
//					if (task != null) {
//						// XXX: HACK TO AVOID OVERWRITE WARNING
//						task.setTaskData(null);
//						TasksUiPlugin.getSynchronizationManager().synchronize(connector, task, true, new JobChangeAdapter() {
//
//							@Override
//							public void done(IJobChangeEvent event) {
//								close();
//								TasksUiUtil.openEditor(task, false);
//							}
//						});
//					} else {
//						//TasksUiUtil.openRepositoryTask(...);
//						close();
//					}
//					return Status.OK_STATUS;
//				} catch (Exception e) {
//					MylarStatusHandler.fail(e, "Error submitting to JIRA server", true);
//					return Status.OK_STATUS;
//				}
//			}
//
//		};
//
//		submitJob.addJobChangeListener(listener);
//		submitJob.schedule();
//	}

	@Override
	protected void validateInput() {
		// TODO Auto-generated method stub
	}
	
	@Override
	protected String getPluginId() {
		return "Jira";
	}

}
