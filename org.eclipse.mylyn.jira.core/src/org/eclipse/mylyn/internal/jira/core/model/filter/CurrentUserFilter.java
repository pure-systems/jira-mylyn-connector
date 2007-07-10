/*******************************************************************************
 * Copyright (c) 2004, 2007 Mylyn project committers and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.eclipse.mylyn.internal.jira.core.model.filter;

// TODO This is going to have to use the username passed into the server
// instance...
// Either that or we can log in and get a session cookie...
/**
 * @author Brock Janiczak
 */
public class CurrentUserFilter extends UserFilter {
	private static final long serialVersionUID = 1L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gbst.jira.core.model.filter.UserFilter#copy()
	 */
	@Override
	UserFilter copy() {
		return new CurrentUserFilter();
	}

}
