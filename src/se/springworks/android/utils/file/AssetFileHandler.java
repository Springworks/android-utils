package se.springworks.android.utils.file;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;

import se.springworks.android.utils.logging.Logger;
import se.springworks.android.utils.logging.LoggerFactory;
import se.springworks.android.utils.stream.StreamUtils;
import android.content.res.AssetManager;

import com.google.inject.Inject;

public class AssetFileHandler implements IAssetFileHandler {

	private static final Logger logger = LoggerFactory.getLogger(AssetFileHandler.class);

	@Inject
	private AssetManager assetManager;
	
	private Hashtable<String, Boolean> fileExistsLookup = new Hashtable<String, Boolean>();


	@Override
	public boolean exists(final String name) {
		logger.debug("exists() " + name);
		if(name == null || name.isEmpty()) {
			return false;
		}
		// file lookups on Assets is slow, cache results
		if(fileExistsLookup.containsKey(name)) {
			return fileExistsLookup.get(name);
		}
		
		final File file = new File(name);
		final String filename = file.getName();
		final String path;
		if(filename.length() == 0) {
			path = name;
		}
		else {
			path = file.getParent();
		}
//		logger.debug("exists() name = " + filename + " path = " + path);

		boolean exists = false;
		String files[] = getFileList(path);
		for (String f : files) {
			if (f.equals(filename)) {
				exists = true;
				break;
			}
		}
		fileExistsLookup.put(name, exists);
		return exists;
	}

	@Override
	public String[] getFileList() {
		return getFileList("");
	}

	@Override
	public String[] getFileList(String path) {
		try {
			return assetManager.list(path);
		} catch (IOException e) {
		}
		return null;
	}

	@Override
	public long getSize(String name) {
		try {
			return this.assetManager.openFd(name).getLength();
		} catch (IOException e) {
		}
		return 0;
	}

	@Override
	public byte[] load(String name) throws IOException {
		InputStream stream = this.assetManager.open(name);
		return StreamUtils.getAsBytes(stream);
	}

	@Override
	public InputStream getReadableFile(String name) throws IOException {
		return this.assetManager.open(name);
	}
}
