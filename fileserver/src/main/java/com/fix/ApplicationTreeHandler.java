package com.fix;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import org.richfaces.model.TreeNode;

/**
 * This class makes operations with tree
 */
public class ApplicationTreeHandler implements Serializable {

    private static final long serialVersionUID = 1L;

    // working directory
    private String directory = "C:\\newFolder";

    // new folder to add
    private String newFolder = "";

    // new file to add
    private String newFile = "";

    // new folder name to change
    private String newFolderName = "";

    // the root of the tree
    private OptionTreeNode stationRoot = new OptionTreeNode();

    // tree nodes
    private OptionTreeNode stationNodes = new OptionTreeNode();

    public ApplicationTreeHandler() {
        initRichFacesTree();
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String aDirectory) {
        directory = aDirectory;
    }

    public String getNewFolder() {
        return newFolder;
    }

    public void setNewFolder(String aNewFolder) {
        newFolder = aNewFolder;
    }

    public String getNewFile() {
        return newFile;
    }

    public void setNewFile(String aNewFile) {
        newFile = aNewFile;
    }

    public String getNewFolderName() {
        return newFolderName;
    }

    public void setNewFolderName(String aNewFolderName) {
        newFolderName = aNewFolderName;
    }

    // changes foder's name
    public void changeFolderName() {
        String old_name = getDirectory();
        File oldfile = new File(old_name);
        String new_name =
                getDirectory().substring(0, getDirectory().lastIndexOf("\\"))
                        + "\\" + getNewFolderName();
        File newfile = new File(new_name);
        oldfile.renameTo(newfile);
        setDirectory(new_name);
        setNewFolderName("");
    }

    // adds new folder
    public void addNewFolder() {
        new File(getDirectory() + "\\" + getNewFolder()).mkdir();
        setNewFolder("");
    }

    // adds new file
    public void addFile() {
        InputStream inStream = null;
        OutputStream outStream = null;
        try {
            File afile = new File(getNewFile());
            File bfile =
                    new File(getDirectory()
                            + getNewFile().substring(
                                    getNewFile().lastIndexOf("\\")));
            inStream = new FileInputStream(afile);
            outStream = new FileOutputStream(bfile);
            byte[] buffer = new byte[1024];
            int length;
            // copy the file content in bytes
            while ((length = inStream.read(buffer)) > 0) {
                outStream.write(buffer, 0, length);
            }
            inStream.close();
            outStream.close();
            setNewFile("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // deletes folder or file
    public void deleteFolderOrFile() {
        File directory = new File(getDirectory());
        delete(directory);
        setDirectory(getDirectory().substring(0,
                getDirectory().lastIndexOf("\\")));
    }

    public static void delete(File file) {
        if (file.isDirectory()) {
            // directory is empty, then delete it
            if (file.list().length == 0) {
                file.delete();
            } else {
                // list all the directory contents
                String files[] = file.list();
                for (String temp : files) {
                    // construct the file structure
                    File fileDelete = new File(file, temp);
                    // recursive delete
                    delete(fileDelete);
                }
                // check the directory again, if empty then delete it
                if (file.list().length == 0) {
                    file.delete();
                }
            }
        } else {
            // if file, then delete it
            file.delete();
        }
    }

    public boolean isDirectoryFile() {
        File file = new File(directory);
        if (file.isFile())
            return true;
        return false;
    }

    public OptionTreeNode getStationNodes() {
        initRichFacesTree();
        return stationNodes;
    }

    // tree initialization
    private void initRichFacesTree() {
        stationRoot = new OptionTreeNode();
        stationNodes = new OptionTreeNode();
        stationRoot.setName(directory);
        stationNodes.addChild(0, stationRoot);
        File folder = new File(directory);
        if (folder.isDirectory()) {
            addFilesToTree(stationRoot, folder);
        }
    }

    private void addFilesToTree(TreeNode node, File folder) {
        int i = 1;
        for (final File fileEntry : folder.listFiles()) {
            OptionTreeNode child = new OptionTreeNode();
            child.setName(fileEntry.getName());
            if (fileEntry.isDirectory()) {
                child.setType("directory");
                addFilesToTree(child, fileEntry);
            } else if (fileEntry.isFile()) {
                child.setType("file");
            }
            node.addChild(i, child);
            i++;
        }
    }
}
