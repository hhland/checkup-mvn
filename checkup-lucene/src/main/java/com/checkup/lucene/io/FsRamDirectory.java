package com.checkup.lucene.io;

import java.io.File;
import java.io.IOException;




import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.IOContext;
import org.apache.lucene.store.NIOFSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

public class FsRamDirectory {


	   private final FSDirectory fsDirectory;
	   private final RAMDirectory ramDirectory;
	   private final  File indexDir;
	   public final IndexWriter ramIndexWriter,fsIndexWriter;
	   //public final IndexSearcher ramIndexSearcher;
	  public final IndexWriterConfig indexWriterConfig;
	   
	   public FsRamDirectory(String path,Analyzer analyzer) throws IOException{
		   IOContext ioContext=new IOContext();
			indexDir=new File(path);
			fsDirectory=NIOFSDirectory.open(indexDir);
			ramDirectory=new RAMDirectory(fsDirectory,ioContext);
			indexWriterConfig=new IndexWriterConfig(Version.LUCENE_CURRENT, analyzer);
			indexWriterConfig.setOpenMode(OpenMode.CREATE_OR_APPEND);
			this.ramIndexWriter=new IndexWriter(ramDirectory, indexWriterConfig);
			this.fsIndexWriter=new IndexWriter(fsDirectory, indexWriterConfig);
			//this.ramIndexSearcher=new IndexSearcher(DirectoryReader.open(ramDirectory));
		}
	   
		public FsRamDirectory(String path,IndexWriterConfig indexWriterConfig) throws IOException{
			IOContext ioContext=new IOContext();
			indexDir=new File(path);
			fsDirectory=NIOFSDirectory.open(indexDir);
			ramDirectory=new RAMDirectory(fsDirectory,ioContext);
			this.indexWriterConfig=indexWriterConfig;
			this.ramIndexWriter=new IndexWriter(ramDirectory, this.indexWriterConfig);
			this.fsIndexWriter=new IndexWriter(fsDirectory, this.indexWriterConfig);
			//this.ramIndexSearcher=new IndexSearcher(DirectoryReader.open(ramDirectory));
		}
		
		
		public File getIndexDir() {
			return indexDir;
		}
		
		
		public IndexSearcher createRamIndexSearcher() throws IOException{
			//unlock();
			return new IndexSearcher(DirectoryReader.open(ramIndexWriter,true));
		}
		
		
		
		
		public void unlock(){
			
			try {
				if(IndexWriter.isLocked(ramDirectory)){
					
					IndexWriter.unlock(ramDirectory);
				}
				if(IndexWriter.isLocked(fsDirectory)){
					//fsDirectory.close();
					//IndexWriter.unlock(fsDirectory);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		public TopDocs  search(String regexp,int n,String... fieldNames) throws IOException{
			IndexSearcher indexSearcher=this.createRamIndexSearcher();
			BooleanQuery booleanQuery=new BooleanQuery();
			for(String fieldName : fieldNames){
				Term term=new Term(fieldName, regexp);
				TermQuery termQuery=new TermQuery(term);
				booleanQuery.add(termQuery, Occur.SHOULD);
			}
			return indexSearcher.search(booleanQuery, n);
		}
		
	
		public void addFileDocuments(String dirPath,boolean justRoot) throws IOException{
			 this.addFileDocuments(new File(dirPath), justRoot);
		   }
		
	   public void addFileDocuments(File dir,boolean justRoot) throws IOException{
		   
		   if(dir.isFile()){
			   ramIndexWriter.addDocument(new FileDocument(dir));
		   }else if(dir.isDirectory()){
			   File[] files=justRoot?dir.listRoots():dir.listFiles();
			   for(File file:files ){
				   try{
				   ramIndexWriter.addDocument(new FileDocument(file));
				   }catch(IOException iox){iox.printStackTrace();}
			   }
		   }
		   
		   ramIndexWriter.commit();
		   
	   }
		
		public void ramToFs() throws Exception{
			
			this.fsIndexWriter.addIndexes(ramDirectory);
			fsIndexWriter.commit();
			
		}
		

		public void close(){
			try{this.ramIndexWriter.close();}catch(Exception ex){}
			try{	this.ramDirectory.close();}catch(Exception ex){}
			try{this.fsIndexWriter.close();}catch(Exception ex){}
			try{	this.fsDirectory.close();}catch(Exception ex){}

		}
		
		

}
