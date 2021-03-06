/**
 * Aptana Studio
 * Copyright (c) 2005-2011 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the GNU Public License (GPL) v3 (with exceptions).
 * Please see the license.html included with this distribution for details.
 * Any modifications to this file must keep this entire header intact.
 */
package com.aptana.editor.haml;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;

import com.aptana.editor.common.CommonDocumentProvider;
import com.aptana.editor.common.CommonEditorPlugin;
import com.aptana.editor.common.ExtendedFastPartitioner;
import com.aptana.editor.common.IExtendedPartitioner;
import com.aptana.editor.common.NullPartitionerSwitchStrategy;
import com.aptana.editor.common.text.rules.CompositePartitionScanner;
import com.aptana.editor.common.text.rules.NullSubPartitionScanner;

/**
 * @author Max Stepanov
 *
 */
public class HAMLDocumentProvider extends CommonDocumentProvider {

	@Override
	public void connect(Object element) throws CoreException
	{
		super.connect(element);

		IDocument document = getDocument(element);
		if (document != null)
		{
			CompositePartitionScanner partitionScanner = new CompositePartitionScanner(
					HAMLSourceConfiguration.getDefault().createSubPartitionScanner(),
					new NullSubPartitionScanner(),
					new NullPartitionerSwitchStrategy());
			IDocumentPartitioner partitioner = new ExtendedFastPartitioner(partitionScanner,
					HAMLSourceConfiguration.getDefault().getContentTypes());
			partitionScanner.setPartitioner((IExtendedPartitioner) partitioner);
			partitioner.connect(document);
			document.setDocumentPartitioner(partitioner);
			CommonEditorPlugin.getDefault().getDocumentScopeManager().registerConfiguration(document,
					HAMLSourceConfiguration.getDefault());
		}
	}

	protected String getDefaultContentType(String filename) {
		return IHAMLConstants.CONTENT_TYPE_HAML;
	}
}
