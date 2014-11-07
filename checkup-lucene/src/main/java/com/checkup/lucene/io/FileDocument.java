package com.checkup.lucene.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.lucene.document.DoubleField;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.FloatField;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.BytesRef;

public  class FileDocument implements Iterable<IndexableField> {

	public static enum FieldName{
		lastModified,length,path,name,extend
	}
	
	public final LongField lastModified,length;
	public final StringField path,name,extend;
	 
	protected final List<IndexableField> fields = new ArrayList<IndexableField>();
	 
	 
     protected File file;
	 
	  public FileDocument(String filePath) throws IOException {
		  this.file=new File(filePath);
		  if(!file.exists()||!file.isFile())throw new IOException();
		  lastModified=new LongField(FieldName.lastModified.name(), file.lastModified(), Store.YES);
		  path=new StringField(FieldName.path.name(), file.getPath(),Store.YES);
		  name=new StringField(FieldName.name.name(), file.getName(), Store.YES);
		  extend=new StringField(FieldName.extend.name(), file.getName().substring(file.getName().lastIndexOf(".")), Store.YES);
		  length=new LongField(FieldName.length.name(), file.length(), Store.YES);
		  
		  initFields();
	  }
	  
	  public FileDocument(File file) throws IOException {
		  this.file=file;
		  if(!file.exists()||!file.isFile())throw new IOException();
		  lastModified=new LongField(FieldName.lastModified.name(), file.lastModified(), Store.YES);
		  path=new StringField(FieldName.path.name(), file.getPath(),Store.YES);
		  name=new StringField(FieldName.name.name(), file.getName(), Store.YES);
		  extend=new StringField(FieldName.extend.name(), file.getName().substring(file.getName().lastIndexOf(".")), Store.YES);
		  length=new LongField(FieldName.length.name(), file.length(), Store.YES);
		  initFields();
	  }
	  
	  
	  protected void initFields(){
		  this.add(lastModified);
		  this.add(path);
		  this.add(name);
		  this.add(length);
		  this.add(extend);
	  }

	  @Override
	  public Iterator<IndexableField> iterator() {
		
	    return fields.iterator();
	  }

	  /**
	   * <p>Adds a field to a document.  Several fields may be added with
	   * the same name.  In this case, if the fields are indexed, their text is
	   * treated as though appended for the purposes of search.</p>
	   * <p> Note that add like the removeField(s) methods only makes sense 
	   * prior to adding a document to an index. These methods cannot
	   * be used to change the content of an existing index! In order to achieve this,
	   * a document has to be deleted from an index and a new changed version of that
	   * document has to be added.</p>
	   */
	  public final void add(IndexableField field) {
	    fields.add(field);
	  }
	  
	  /**
	   * <p>Removes field with the specified name from the document.
	   * If multiple fields exist with this name, this method removes the first field that has been added.
	   * If there is no field with the specified name, the document remains unchanged.</p>
	   * <p> Note that the removeField(s) methods like the add method only make sense 
	   * prior to adding a document to an index. These methods cannot
	   * be used to change the content of an existing index! In order to achieve this,
	   * a document has to be deleted from an index and a new changed version of that
	   * document has to be added.</p>
	   */
	  public final void removeField(String name) {
	    Iterator<IndexableField> it = fields.iterator();
	    while (it.hasNext()) {
	      IndexableField field = it.next();
	      if (field.name().equals(name)) {
	        it.remove();
	        return;
	      }
	    }
	  }
	  
	  /**
	   * <p>Removes all fields with the given name from the document.
	   * If there is no field with the specified name, the document remains unchanged.</p>
	   * <p> Note that the removeField(s) methods like the add method only make sense 
	   * prior to adding a document to an index. These methods cannot
	   * be used to change the content of an existing index! In order to achieve this,
	   * a document has to be deleted from an index and a new changed version of that
	   * document has to be added.</p>
	   */
	  public final void removeFields(String name) {
	    Iterator<IndexableField> it = fields.iterator();
	    while (it.hasNext()) {
	      IndexableField field = it.next();
	      if (field.name().equals(name)) {
	        it.remove();
	      }
	    }
	  }


	  /**
	  * Returns an array of byte arrays for of the fields that have the name specified
	  * as the method parameter.  This method returns an empty
	  * array when there are no matching fields.  It never
	  * returns null.
	  *
	  * @param name the name of the field
	  * @return a <code>BytesRef[]</code> of binary field values
	  */
	  public final BytesRef[] getBinaryValues(String name) {
	    final List<BytesRef> result = new ArrayList<BytesRef>();
	    for (IndexableField field : fields) {
	      if (field.name().equals(name)) {
	        final BytesRef bytes = field.binaryValue();
	        if (bytes != null) {
	          result.add(bytes);
	        }
	      }
	    }
	  
	    return result.toArray(new BytesRef[result.size()]);
	  }
	  
	  /**
	  * Returns an array of bytes for the first (or only) field that has the name
	  * specified as the method parameter. This method will return <code>null</code>
	  * if no binary fields with the specified name are available.
	  * There may be non-binary fields with the same name.
	  *
	  * @param name the name of the field.
	  * @return a <code>BytesRef</code> containing the binary field value or <code>null</code>
	  */
	  public final BytesRef getBinaryValue(String name) {
	    for (IndexableField field : fields) {
	      if (field.name().equals(name)) {
	        final BytesRef bytes = field.binaryValue();
	        if (bytes != null) {
	          return bytes;
	        }
	      }
	    }
	    return null;
	  }

	  /** Returns a field with the given name if any exist in this document, or
	   * null.  If multiple fields exists with this name, this method returns the
	   * first value added.
	   */
	  public final IndexableField getField(String name) {
	    for (IndexableField field : fields) {
	      if (field.name().equals(name)) {
	        return field;
	      }
	    }
	    return null;
	  }

	  /**
	   * Returns an array of {@link IndexableField}s with the given name.
	   * This method returns an empty array when there are no
	   * matching fields.  It never returns null.
	   *
	   * @param name the name of the field
	   * @return a <code>IndexableField[]</code> array
	   */
	  public IndexableField[] getFields(String name) {
	    List<IndexableField> result = new ArrayList<IndexableField>();
	    for (IndexableField field : fields) {
	      if (field.name().equals(name)) {
	        result.add(field);
	      }
	    }

	    return result.toArray(new IndexableField[result.size()]);
	  }
	  
	  /** Returns a List of all the fields in a document.
	   * <p>Note that fields which are <i>not</i> stored are
	   * <i>not</i> available in documents retrieved from the
	   * index, e.g. {@link IndexSearcher#doc(int)} or {@link
	   * IndexReader#document(int)}.
	   */
	  public final List<IndexableField> getFields() {
	    return fields;
	  }
	  
	   private final static String[] NO_STRINGS = new String[0];

	  /**
	   * Returns an array of values of the field specified as the method parameter.
	   * This method returns an empty array when there are no
	   * matching fields.  It never returns null.
	   * For {@link IntField}, {@link LongField}, {@link
	   * FloatField} and {@link DoubleField} it returns the string value of the number. If you want
	   * the actual numeric field instances back, use {@link #getFields}.
	   * @param name the name of the field
	   * @return a <code>String[]</code> of field values
	   */
	  public final String[] getValues(String name) {
	    List<String> result = new ArrayList<String>();
	    for (IndexableField field : fields) {
	      if (field.name().equals(name) && field.stringValue() != null) {
	        result.add(field.stringValue());
	      }
	    }
	    
	    if (result.size() == 0) {
	      return NO_STRINGS;
	    }
	    
	    return result.toArray(new String[result.size()]);
	  }

	  /** Returns the string value of the field with the given name if any exist in
	   * this document, or null.  If multiple fields exist with this name, this
	   * method returns the first value added. If only binary fields with this name
	   * exist, returns null.
	   * For {@link IntField}, {@link LongField}, {@link
	   * FloatField} and {@link DoubleField} it returns the string value of the number. If you want
	   * the actual numeric field instance back, use {@link #getField}.
	   */
	  public final String get(String name) {
	    for (IndexableField field : fields) {
	      if (field.name().equals(name) && field.stringValue() != null) {
	        return field.stringValue();
	      }
	    }
	    return null;
	  }
	  
	  /** Prints the fields of a document for human consumption. */
	  @Override
	  public final String toString() {
	    StringBuilder buffer = new StringBuilder();
	    buffer.append("Document<");
	    for (int i = 0; i < fields.size(); i++) {
	      IndexableField field = fields.get(i);
	      buffer.append(field.toString());
	      if (i != fields.size()-1)
	        buffer.append(" ");
	    }
	    buffer.append(">");
	    return buffer.toString();
	  }

}
