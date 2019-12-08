package org.mycompany.myname.Parsers;

import org.mycompany.myname.OutputInfo;

public interface Parser {
    public OutputInfo getPage() throws InterruptedException;
}
