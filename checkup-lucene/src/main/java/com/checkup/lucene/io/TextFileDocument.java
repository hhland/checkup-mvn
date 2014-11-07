package com.checkup.lucene.io;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.lucene.document.TextField;

public class TextFileDocument extends FileDocument {

	public static enum FieldName{
		context
	}
	public TextField context;
	
	public TextFileDocument(String filePath) throws IOException {
		super(filePath);
		
		// TODO Auto-generated constructor stub
		context=new TextField(FieldName.context.name(), new FileReader(this.file) );
		this.fields.add(context);
	}

	public TextFileDocument(File file) throws IOException {
		super(file);
		// TODO Auto-generated constructor stub
		context=new TextField(FieldName.context.name(), new FileReader(this.file) );
		this.fields.add(context);
	}

	
	
	

}
