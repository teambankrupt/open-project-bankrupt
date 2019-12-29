package com.example.webservice.commons;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class Commons {
    private Commons() {
    }

    public static List<String> deleteImageAndRebuildPaths(List<String> imagePaths, int imageIndex) {
        List<String> newPaths = new ArrayList<>();
        for (int i = 0; i < imagePaths.size(); i++) {
            if (i != imageIndex)
                newPaths.add(imagePaths.get(i));
            else {
                File file = new File(imagePaths.get(imageIndex));
                if (file.exists()) file.delete();
            }
        }
        return newPaths;
    }


    public static <T> T getLastElement(final Iterable<T> elements) {
        final Iterator<T> itr = elements.iterator();
        T lastElement = itr.next();

        while (itr.hasNext()) {
            lastElement = itr.next();
        }

        return lastElement;
    }

}
