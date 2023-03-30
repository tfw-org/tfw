package tfw.immutable.ilm.stringilm;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;

public final class StringIlmSegment
{
    private StringIlmSegment() { }

    public static StringIlm create(StringIlm instance,
    	long rowStart, long columnStart)
    {
		return create(instance, rowStart, columnStart,
			instance.width() - columnStart, instance.height() - rowStart);
    }

    public static StringIlm create(StringIlm instance, long rowStart,
    	long columnStart, long width, long height)
    {
    	Argument.assertNotNull(instance, "instance");
    	Argument.assertNotLessThan(rowStart, 0, "rowStart");
    	Argument.assertNotLessThan(columnStart, 0, "columnStart");
    	Argument.assertNotLessThan(width, 0, "width");
    	Argument.assertNotLessThan(height, 0, "height");
    	Argument.assertNotGreaterThan((rowStart + height), instance.height(),
    		"rowStart + height", "instance.height()");
    	Argument.assertNotGreaterThan((columnStart + width), instance.width(),
    		"columnStart + width", "instance.width()");

		return new MyStringIlm(instance, rowStart, columnStart, width, height);
    }

    private static class MyStringIlm extends AbstractStringIlm
    	implements ImmutableProxy
    {
		private final StringIlm instance;
		private final long rowStart;
		private final long columnStart;

		MyStringIlm(StringIlm instance, long rowStart, long columnStart,
			long width, long height)
		{
		    super(width, height);
		    
		    this.instance = instance;
		    this.rowStart = rowStart;
		    this.columnStart = columnStart;
		}

		protected void toArrayImpl(String[][] array, int rowOffset,
			int columnOffset, long rowStart, long columnStart,
			int width, int height) throws DataInvalidException
		{
			instance.toArray(array, rowOffset, columnOffset,
				this.rowStart + rowStart, this.columnStart + columnStart,
				width, height);
		}
		
		public Map getParameters()
		{
			HashMap map = new HashMap();
			
			map.put("name", "StringIlmSegment");
			map.put("rowStart", new Long(rowStart));
			map.put("columnStart", new Long(columnStart));
			map.put("width", new Long(width()));
			map.put("height", new Long(height()));
			
			return(map);
		}
    }
}