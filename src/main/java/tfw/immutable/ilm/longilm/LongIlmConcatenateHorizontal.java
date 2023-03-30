package tfw.immutable.ilm.longilm;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;

public final class LongIlmConcatenateHorizontal
{
    private LongIlmConcatenateHorizontal() {}

    public static LongIlm create(LongIlm leftIlm, LongIlm rightIlm)
    {
    	Argument.assertNotNull(leftIlm, "leftIlm");
    	Argument.assertNotNull(rightIlm, "rightIlm");
    	Argument.assertEquals(leftIlm.height(), rightIlm.height(),
    		"leftIlm.height()", "rightIlm.height()");

		return new MyLongIlm(leftIlm, rightIlm);
    }

    private static class MyLongIlm extends AbstractLongIlm
    	implements ImmutableProxy
    {
		private LongIlm leftIlm;
		private LongIlm rightIlm;

		MyLongIlm(LongIlm leftIlm, LongIlm rightIlm)
		{
		    super(leftIlm.width() + rightIlm.width(), leftIlm.height());
		    
		    this.leftIlm = leftIlm;
		    this.rightIlm = rightIlm;
		}
		
		protected void toArrayImpl(long[][] array, int rowOffset,
			int columnOffset, long rowStart, long columnStart,
			int width, int height) throws DataInvalidException
		{
		    if (columnStart + width <= leftIlm.width())
		    {
				leftIlm.toArray(array, rowOffset, columnOffset,
					rowStart, columnStart, width, height);
		    }
		    else if (columnStart >= leftIlm.width())
			{
				rightIlm.toArray(array, rowOffset, columnOffset,
					rowStart, columnStart - leftIlm.width(), width, height);
		    }
		    else
			{
				int firstamount = (int)(leftIlm.width() - columnStart);
				leftIlm.toArray(array, rowOffset, columnOffset,
					rowStart, columnStart, firstamount, height);
				rightIlm.toArray(array, rowOffset, columnOffset + firstamount,
					rowStart, 0, width - firstamount, height);
	    	}
		}
		
		public Map getParameters()
		{
			HashMap map = new HashMap();
			
			map.put("name", "LongIlmConcatenateHorizontal");
			map.put("leftIlm", getImmutableInfo(leftIlm));
			map.put("rightIlm", getImmutableInfo(rightIlm));
			map.put("width", new Long(width()));
			map.put("height", new Long(height()));
			
			return(map);
		}
    }
}