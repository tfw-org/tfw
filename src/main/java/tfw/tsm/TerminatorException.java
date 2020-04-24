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
 * without even the implied warranty of
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
package tfw.tsm;

/**
 * Represents a problem with termination, such as a sink or source
 * which is in compatible with the event channel value constraint.
 */
class TerminatorException extends RuntimeException {
	
	/**
	 * Creates an exception with the specified message.
	 * @param message the exception message
	 */
	public TerminatorException(String message){
		super(message);
	}
	
	/**
	 * Creates an exception with the specified attributes.
	 * @param message the exception message
	 * @param cause the cause.
	 */
	public TerminatorException(String message, Throwable cause){
		super(message, cause);
	}
}
