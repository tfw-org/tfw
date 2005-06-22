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
package tfw.tsm.ecd;

import tfw.value.ValueCodec;
import tfw.value.ValueConstraint;

/**
 * The base class for all event channels that are used to set state as part 
 * of a transaction rollback. Note that the state is changed in a follow-on
 * transaction.
 */
public abstract class RollbackECD extends EventChannelDescription {
	public RollbackECD(String eventChannelName, ValueConstraint constraint, ValueCodec codec){
		super(eventChannelName, constraint, codec);
	}
}
