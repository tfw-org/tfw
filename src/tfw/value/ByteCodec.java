/*
 * The Framework Project
 * Copyright (C) 2005 Anonymous
 * 
 * This library is free software; you can
 * redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your
 * option) any later version.
 * 
 * This library is distributed in the hope that it
 * will be useful, but WITHOUT ANY WARRANTY;
 * witout even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.  See the GNU Lesser General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU
 * Lesser General Public License along with this
 * library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307 USA
 */
package tfw.value;

import tfw.check.Argument;

/**
 * Codec for encoding and decoding bytes.
 */
public class ByteCodec implements ValueCodec {
	public static final ByteCodec INSTANCE = new ByteCodec();

	/**
	 * Hide the constructor because we only need one instance.
	 */
	private ByteCodec()
	{
	}

	/**
	 * Returns an instance of a byte codec.
	 * @return an instance of a byte codec.
	 */
	public static ByteCodec getInstance()
	{
		return INSTANCE;
	}

	/* (non-Javadoc)
	 * @see co2.value.ValueCodec#decode(java.lang.String)
	 */
	public Object decode(String value) throws CodecException {
		Argument.assertNotNull(value, "value");
		
		try{
			return new Byte(value);
		} catch (NumberFormatException nfe){
			throw new CodecException("Error decoding byte value '" + value + "'.");
		}
	}

	/* (non-Javadoc)
	 * @see co2.value.ValueCodec#encode(java.lang.Object)
	 */
	public String encode(Object value) throws CodecException {
		Argument.assertNotNull(value, "value");
		if (!(value instanceof Byte))
		{
			throw new CodecException(
				"Error encoding value. The specified value is not a java.lang.Byte");
		}
		return value.toString();
	}

}
