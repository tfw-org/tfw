package tfw.immutable.ilm.objectilm;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;

public final class ObjectIlmConcatenateVertical
{
    private ObjectIlmConcatenateVertical() {}

    public static ObjectIlm create(ObjectIlm topIlm, ObjectIlm bottomIlm)
    {
    	Argument.assertNotNull(topIlm, "topIlm");
    	Argument.assertNotNull(bottomIlm, "bottomIlm");
    	Argument.assertEquals(topIlm.width(), bottomIlm.width(),
    		"topIlm.width()", "bottomIlm.width()");

		return new MyObjectIlm(topIlm, bottomIlm);
    }

    private static class MyObjectIlm extends AbstractObjectIlm
    	implements ImmutableProxy
    {
		private ObjectIlm topIlm;
		private ObjectIlm bottomIlm;

		MyObjectIlm(ObjectIlm topIlm, ObjectIlm bottomIlm)
		{
		    super(topIlm.width(), topIlm.height() + bottomIlm.height());
		    
		    this.topIlm = topIlm;
		    this.bottomIlm = bottomIlm;
		}
		
		protected void toArrayImpl(Object[][] array, int rowOffset,
			int columnOffset, long rowStart, long columnStart,
			int width, int height) throws DataInvalidException
		{
		    if (rowStart + height <= topIlm.height())
		    {
				topIlm.toArray(array, rowOffset, columnOffset,
					rowStart, columnStart, width, height);
		    }
		    else if (rowStart >= topIlm.height())
			{
				bottomIlm.toArray(array, rowOffset, columnOffset,
					rowStart - topIlm.height(), columnStart, width, height);
		    }
		    else
			{
				int firstamount = (int)(topIlm.height() - rowStart);
				topIlm.toArray(array, rowOffset, columnOffset,
					rowStart, columnStart, width, firstamount);
				bottomIlm.toArray(array, rowOffset + firstamount, columnOffset,
					0, columnStart, width, height - firstamount);
	    	}
		}
		
		public Map getParameters()
		{
			HashMap map = new HashMap();
			
			map.put("name", "ObjectIlmConcatenateVertical");
			map.put("topIlm", getImmutableInfo(topIlm));
			map.put("bottomIlm", getImmutableInfo(bottomIlm));
			map.put("width", new Long(width()));
			map.put("height", new Long(height()));
			
			return(map);
		}
    }
}