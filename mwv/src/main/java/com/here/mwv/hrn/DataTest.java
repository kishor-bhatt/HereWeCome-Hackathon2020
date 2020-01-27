package com.here.mwv.hrn;


import static java.lang.System.out;

import com.here.hrn.HRN;

public class DataTest {

    public static void main(String[] args) {
        // HRN for the simulated sensor hrn at
        // https://platform.here.com/data/hrn:here:data:::olp-sdii-sample-berlin-2
        HRN hrn = HRN.fromString("hrn:here:hrn::olp-here:rib-2");
        out.println("HRN: \"" + hrn + "\"");
        out.println("\tpartition: \"" + hrn.partition() + "\"");
        out.println("\tservice: \"" + hrn.service() + "\"");
        out.println("\tregion: \"" + hrn.region() + "\"");
        out.println("\taccount: \"" + hrn.account() + "\"");
        out.println("\tresource: \"" + hrn.resource() + "\"");
    }
}

