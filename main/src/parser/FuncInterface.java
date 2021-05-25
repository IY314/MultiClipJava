package parser;

import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public interface FuncInterface {
	String call(String ...s) throws IOException, UnsupportedFlavorException;
}
