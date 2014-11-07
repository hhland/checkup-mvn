package com.checkup.lucene.io;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.StringField;



public class ImgFileDocument extends FileDocument {

	public enum FieldType{
		height,width;
	}
	
	private final BufferedImage  image;
	public final IntField height,width;
	public final StringField[] propertys;
	
	public ImgFileDocument(File file) throws IOException {
		super(file);
		// TODO Auto-generated constructor stub
		 image=ImageIO.read(file);
		 height=new IntField(FieldType.height.name(), image.getHeight(), Store.YES);
		 width=new IntField(FieldType.width.name(), image.getWidth(), Store.YES);
		 this.fields.add(height);
		 this.fields.add(width);
		 String[] names=image.getPropertyNames();
		 propertys=new StringField[names.length];
		 for(int i=0;i<names.length;i++){
			 String name=names[i];
			 String val=image.getProperty(name).toString();
			 propertys[i]=new StringField(name, val, Store.YES);
			 this.fields.add(propertys[i]);
		 }
	}

	public ImgFileDocument(String filePath) throws IOException {
		super(filePath);
		// TODO Auto-generated constructor stub
		// TODO Auto-generated constructor stub
				 image=ImageIO.read(file);
				 height=new IntField(FieldType.height.name(), image.getHeight(), Store.YES);
				 width=new IntField(FieldType.width.name(), image.getWidth(), Store.YES);
				 this.fields.add(height);
				 this.fields.add(width);
				 String[] names=image.getPropertyNames();
				 propertys=new StringField[names.length];
				 for(int i=0;i<names.length;i++){
					 String name=names[i];
					 String val=image.getProperty(name).toString();
					 propertys[i]=new StringField(name, val, Store.YES);
					 this.fields.add(propertys[i]);
				 }
	}
	
	

}
