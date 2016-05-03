/*
	CallTreeBrowser. A simple tool to browse call trees.
	Copyright (C) 2016  Mikael Ågren

	This program is free software: you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.

	This program is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.

	You should have received a copy of the GNU General Public License
	along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DotReader {
	/**
	 * Read DOT-style graph-data.
	 * Reads 'reader' line by line looking for a 'caller -> callee;' pattern; 
	 * @param reader
	 * @return           A map mapping each caller name to a list of callee names.
	 * @throws IOException
	 */
	public static HashMap<String, ArrayList<String>> readDot(BufferedReader reader) throws IOException {
		HashMap<String, ArrayList<String>> callMap = new HashMap<String, ArrayList<String>>();
		String line;
		Pattern callerCalleePattern = Pattern.compile("\\s*(\\w+)\\s*->\\s*(.+)\\s*;", Pattern.UNICODE_CHARACTER_CLASS);
		while ((line = reader.readLine()) != null) {
			line = line.split("//")[0];
			Matcher callerCalleeMatcher = callerCalleePattern.matcher(line);
			if (callerCalleeMatcher.matches()) {
				String callerName = callerCalleeMatcher.group(1);
				String calleeName = callerCalleeMatcher.group(2);
				ArrayList<String> calleeList = callMap.get(callerName);
				if (calleeList == null) {
					calleeList = new ArrayList<String>();
					callMap.put(callerName, calleeList);
				}
				if (!calleeList.contains(calleeName)) {
					calleeList.add(calleeName);
				}
			}
		}
		return callMap;
	}
}
