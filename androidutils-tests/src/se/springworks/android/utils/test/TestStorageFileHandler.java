package se.springworks.android.utils.test;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import se.springworks.android.utils.file.StorageFileHandler;
import android.test.AndroidTestCase;

public class TestStorageFileHandler extends AndroidTestCase {
	
	private StorageFileHandler fileHandler;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Override
	@Before
	public void setUp() throws Exception {
		fileHandler = new StorageFileHandler(getContext());
	}

	@Override
	@After
	public void tearDown() throws Exception {
		fileHandler.deleteDir(".");
	}
	
	
	@Test
	public void testCreateEmptyFile() {
		assertFalse(fileHandler.createEmptyFile("../../../../../../../*"));
		
		final String filename = "emptyfile";
		assertFalse(fileHandler.exists(filename));
		fileHandler.createEmptyFile(filename);
		assertTrue(fileHandler.exists(filename));
		assertTrue(fileHandler.getSize(filename) == 0);
	}
	
	@Test
	public void testDelete() throws IOException {
		assertFalse(fileHandler.delete("doesnotexist"));
		
		final String filename = "filetodelete";
		assertFalse(fileHandler.exists(filename));
		fileHandler.createEmptyFile(filename);
		assertTrue(fileHandler.exists(filename));
		assertTrue(fileHandler.delete(filename));
		assertFalse(fileHandler.delete(filename));
	}
	
	@Test
	public void testGetFileList() {
		fileHandler.createEmptyFile("file1");
		fileHandler.createEmptyFile("file2");
		fileHandler.createEmptyFile("file3");
		fileHandler.createEmptyFile("folder1/file1");
		fileHandler.createEmptyFile("folder1/file2");
		fileHandler.createEmptyFile("folder2/file1");
		String[] files = fileHandler.getFileList();
		assertNotNull(files);
		assertEquals(5, files.length);		
		ArrayList<String> filesAsString = new ArrayList<String>(Arrays.asList(files));
		assertTrue(filesAsString.contains("file1"));
		assertTrue(filesAsString.contains("file2"));
		assertTrue(filesAsString.contains("file3"));
		assertTrue(filesAsString.contains("folder1"));
		assertTrue(filesAsString.contains("folder2"));

	
		files = fileHandler.getFileList("folder1/");
		assertNotNull(files);
		assertEquals(2, files.length);
		filesAsString = new ArrayList<String>(Arrays.asList(files));
		assertTrue(filesAsString.contains("file1"));
		assertTrue(filesAsString.contains("file2"));

		files = fileHandler.getFileList("folder2/");
		assertNotNull(files);
		assertEquals(1, files.length);
		filesAsString = new ArrayList<String>(Arrays.asList(files));
		assertTrue(filesAsString.contains("file1"));	
	}

	
	@Test
	public void testDeleteDir() throws IOException {
		fileHandler.createEmptyFile("some/path1/file.txt");
		fileHandler.createEmptyFile("some/path2/file.txt");
		assertTrue(fileHandler.exists("some"));
		assertTrue(fileHandler.exists("some/path1"));
		assertTrue(fileHandler.exists("some/path1/file.txt"));
		assertTrue(fileHandler.exists("some/path2/file.txt"));
		fileHandler.deleteDir("some/path1");
		assertTrue(fileHandler.exists("some"));
		assertFalse(fileHandler.exists("some/path1"));
		assertFalse(fileHandler.exists("some/path1/file.txt"));
		assertTrue(fileHandler.exists("some/path2/file.txt"));
	}
	
	
	@Test
	public void testMemory() throws IOException {
		final long total = fileHandler.getTotalMemory();
		final long available = fileHandler.getAvailableMemory();
		assertTrue(available <= total);
		
		final String TEXTTOSAVEASSTRING = "ABCDEFGH";
		fileHandler.save("file", TEXTTOSAVEASSTRING);
		final long availableNow = fileHandler.getAvailableMemory();
		assertTrue(availableNow < available);
		
		fileHandler.delete("file");
		assertEquals(fileHandler.getAvailableMemory(), available);
	}


	@Test
	public void testGetWritableAndReadableFile() throws IOException {
		final String filename = "fileasstream";
		OutputStream out = fileHandler.getWritableFile(filename, false);
		assertNotNull(out);
		DataOutputStream dos = new DataOutputStream(out);
		dos.writeUTF("SOMESTRING");
		dos.flush();
		dos.close();
		
		assertTrue(fileHandler.exists(filename));		
		
		InputStream in = fileHandler.getReadableFile(filename);
		assertNotNull(in);
		DataInputStream dis = new DataInputStream(in);
		assertEquals("SOMESTRING", dis.readUTF());
		dis.close();

		out = fileHandler.getWritableFile(filename, true);
		dos = new DataOutputStream(out);
		dos.writeUTF("SOMEOTHERSTRING");
		dos.flush();
		dos.close();
		in = fileHandler.getReadableFile(filename);
		dis = new DataInputStream(in);
		assertEquals("SOMESTRING", dis.readUTF());
		assertEquals("SOMEOTHERSTRING", dis.readUTF());
		dis.close();
	}
	
	@Test
	public void testGetSize() throws IOException {
		assertEquals(-1, fileHandler.getSize("doesnotexist"));
		final String filename = "filewithfourbytes";
		OutputStream out = fileHandler.getWritableFile(filename, false);
		out.write(1);
		out.write(2);
		out.write(3);
		out.write(4);
		out.close();
		assertEquals(4, fileHandler.getSize(filename));
		
		fileHandler.createEmptyFile("emptyfile");
		assertEquals(0, fileHandler.getSize("emptyfile"));
	}
	
	
	@Test
	public void testSaveAndLoadBytes() throws IOException {
		final String filename = "filewithfourbytes";
		byte[] bytes = {1,20,20,4};
		fileHandler.save(filename, bytes);
		assertTrue(fileHandler.exists(filename));		
		
		bytes = fileHandler.load(filename);
		assertNotNull(bytes);
		assertEquals(1, bytes[0]);
		assertEquals(20, bytes[1]);
		assertEquals(20, bytes[2]);
		assertEquals(4, bytes[3]);
	}

	
	@Test
	public void testSaveInputStreamAndLoadString() throws IOException {
		final String filename = "filewithstring";
	
		String stringtosave = "SOMESTRING";
		fileHandler.save(filename, new ByteArrayInputStream(stringtosave.getBytes()));
		assertTrue(fileHandler.exists(filename));		
		
		assertEquals("SOMESTRING", new String(fileHandler.load(filename)));
	}
	
	@Test
	public void testSaveStringAndLoadString() throws IOException {
		final String filename = "filewithstring";
	
		String stringtosave = "SOMESTRING";
		fileHandler.save(filename, stringtosave);
		assertTrue(fileHandler.exists(filename));		
		
		assertEquals("SOMESTRING", new String(fileHandler.load(filename)));
	}
	
	
	@Test
	public void testGetFileModifiedDate() {
		// note, there's not point testing further since there's a problem with some devices not being able to set
		// an accurate file modification date. Need to test this more using the ImprovedStorageFileHandler
		fileHandler.createEmptyFile("emptyfile");
		assertTrue(fileHandler.getFileModifiedDate("emptyfile") instanceof Date);
		assertNull(fileHandler.getFileModifiedDate("doesnotexist"));
	}
	
	@Test
	public void testSetFileModifiedDate() {
		// note, there's not point testing further since there's a problem with some devices not being able to set
		// an accurate file modification date. Need to test this more using the ImprovedStorageFileHandler
		assertFalse(fileHandler.setFileModifiedTime("doesnotexist", 123456));

		fileHandler.createEmptyFile("emptyfile");
		assertTrue(fileHandler.setFileModifiedTime("emptyfile", 123456));
	}
}
