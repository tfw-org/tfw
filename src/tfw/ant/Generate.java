package tfw.ant;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.taskdefs.MatchingTask;
import org.apache.tools.ant.types.Path;

public class Generate extends MatchingTask
{
    private static final Pattern typesPattern = Pattern
        .compile("@immutables.types=(\\w+)");

    private static final Pattern packagePattern = Pattern
        .compile("^\\s*package\\s+(.*);");

    private Path src = null;

    public void setSrcdir(Path srcDir)
    {
        this.src = srcDir;
    }

    private File dest = null;

    public void setDestdir(File destDir)
    {
        this.dest = destDir;
    }

    private void checkParameters() throws BuildException
    {
        if(src == null)
            throw new BuildException("srcdir attribute must be set",
                getLocation());
        if(src.size() == 0)
            throw new BuildException("srcdir attribute must be set",
                getLocation());
        if(dest == null)
            throw new BuildException("destdir attribute must be set",
                getLocation());
        if(!dest.isDirectory())
            throw new BuildException("destdir " + dest
                + " does not exist " + "or is not a directory",
                getLocation());
    }

    private Hashtable properties;

    private Pattern[] template;

    private String[] property;

    private String markerString;

    private void fillInProperties() throws BuildException
    {
        properties = getProject().getProperties();
        String categoriesString = (String) properties
            .get("immutables.categories");
        if(categoriesString == null)
            throw new BuildException(
                "property immutables.categories not defined",
                getLocation());
        String[] categories = categoriesString.replaceAll("\\s+", "")
            .split(",");
        template = new Pattern[categories.length];
        property = new String[categories.length];
        for(int ii = 0; ii < categories.length; ++ii)
        {
            template[ii] = Pattern.compile("%%"
                + categories[ii].toUpperCase() + "%%");
            property[ii] = categories[ii].toLowerCase();
        }
        markerString = (String) properties
            .get("immutables.autogen");
        if(markerString == null)
            throw new BuildException(
                "property immutables.autogen not defined",
                getLocation());
    }

    public void execute() throws BuildException
    {
	try {
        checkParameters();
        fillInProperties();
        String[] list = src.list();
        long total = 0;
        for(int ii = 0; ii < list.length; ++ii)
        {
            File srcDir = getProject().resolveFile(list[ii]);
            if(!srcDir.exists())
                throw new BuildException("srcdir " + srcDir.getPath()
                    + " does not exist " + "or is not a directory",
                    getLocation());
            DirectoryScanner ds = getDirectoryScanner(srcDir);
            String[] files = ds.getIncludedFiles();
            log("Generating from " + files.length + " template file"
                + (files.length == 1 ? "" : "s") + " in "
                + template.length + " categor"
                + (template.length == 1 ? "y" : "ies") + " to " + dest);
            for(int jj = 0; jj < files.length; ++jj)
            {
                total += processFile(srcDir, files[jj]);
            }
        }
        log(total + " total Java file" + (total == 1 ? "" : "s")
            + " generated from templates");
	} catch(StringIndexOutOfBoundsException e) {
	    e.printStackTrace();
	    throw e;
	}
    }

    private String[] preprocess(File file) throws BuildException
    {
        String[] result = new String[2];
        BufferedReader in = null;
        try
        {
            in = new BufferedReader(new FileReader(file));
        }
        catch(FileNotFoundException fnfe)
        {
            throw new BuildException("input file " + file
                + " not found", getLocation());
        }
        String line;
        try
        {
            while((result[0] == null || result[1] == null)
                && (line = in.readLine()) != null)
            {
                String test = findLine(typesPattern, line);
                if(test != null)
                {
                    result[0] = test;
                }
                test = findLine(packagePattern, line);
                if(test != null)
                {
                    result[1] = test;
                }
            }
        }
        catch(IOException ioe)
        {
            throw new BuildException("error procesing input file "
                + file + ": " + ioe.getMessage(), getLocation());
        }
        try
        {
            in.close();
        }
        catch(IOException ioe)
        {
            throw new BuildException(
                "error closing input file " + file, getLocation());
        }
        return result;
    }

    private String[] extractTypes(String alias)
    {
        String[] result = null;
        String typesPath = "immutables.types." + alias;
        String value = (String) properties.get(typesPath);
        if(value != null)
        {
            result = value.replaceAll("\\s+", "").toLowerCase().split(
                ",");
        }
        return result;
    }

    private long processFile(File srcDir, String pathTo)
        throws BuildException
    {
        long total = 0;
        File infile = new File(srcDir, pathTo);
        String[] result = preprocess(infile);
        String alias = result[0];
        String packag = result[1];
        if(alias == null)
            throw new BuildException("template file " + infile
                + " does not contain type information", getLocation());
        if(packag == null)
            throw new BuildException("template file " + infile
                + " does not specify a package", getLocation());
        String[] types = extractTypes(alias);
        if(types == null)
            throw new BuildException("type alias immutables.types."
                + alias + " in file " + infile
                + " not described in properties", getLocation());
        PrintWriter outs[] = new PrintWriter[types.length];
        for(int ii = 0; ii < types.length; ++ii)
        {
            File outfile = determineOutFile(types[ii], packag, infile);
            if(!(outfile.exists() && outfile.lastModified() > infile
                .lastModified()))
            {
                File parent = outfile.getParentFile();
                if(parent != null)
                {
                    parent.mkdirs();
                }
                try
                {
                    outs[ii] = new PrintWriter(new FileWriter(outfile));
                }
                catch(IOException ioe)
                {
                    throw new BuildException(
                        "cannot create output file " + outfile,
                        getLocation());
                }
            }
        }
        BufferedReader in = null;
        try
        {
            in = new BufferedReader(new FileReader(infile));
        }
        catch(FileNotFoundException fnfe)
        {
            throw new BuildException("input file " + infile
                + " not found", getLocation());
        }
        String line;
        try
        {
            while((line = in.readLine()) != null)
            {
                for(int ii = 0; ii < types.length; ++ii)
                {
                    if(outs[ii] != null)
                    {
                        String outline = untemplate(line, types[ii]);
                        outs[ii].println(outline);
                    }
                }
            }
        }
        catch(IOException ioe)
        {
            throw new BuildException("error procesing input file "
                + infile + ": " + ioe.getMessage(), getLocation());
        }
        try
        {
            in.close();
        }
        catch(IOException ioe)
        {
            throw new BuildException("error closing input file "
                + infile, getLocation());
        }
        for(int ii = 0; ii < types.length; ++ii)
        {
            if(outs[ii] != null)
            {
                outs[ii].println(markerString);
                outs[ii].close();
                ++total;
            }
        }
        return total;
    }

    private File determineOutFile(String type, String packag, File file)
    {
	String separatorReplacement = ("\\".equals(File.separator) ?
				       File.separator + File.separator :
				       File.separator);
        String realPackag = untemplate(packag, type).replaceAll("\\.",
            separatorReplacement);
        String immutableName = (String) properties.get("immutables."
            + type + "." + property[0]);
        String realName = file.getName()
            .replaceAll("__", immutableName).replaceFirst(
                "\\.template", ".java");
        File result = new File(dest, realPackag + File.separator
            + realName);
        return result;
    }

    private String untemplate(String line, String type)
    {
        for(int ii = 0; ii < template.length; ++ii)
        {
            Matcher matcher = template[ii].matcher(line);
            String replacement = (String) properties.get("immutables."
                + type + "." + property[ii]);
            line = matcher.replaceAll(replacement);
        }
        return line;
    }

    private String findLine(Pattern pattern, String line)
    {
        Matcher matcher = pattern.matcher(line);
        if(matcher.find())
        {
            return matcher.group(1);
        }
        else
        {
            return null;
        }
    }
}
