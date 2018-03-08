package com.furyviewer.service.OpenMovieDatabase.Service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
public class AsyncImportTasks {
    public AsyncImportTasks() {
    }

    @Async
    public void importAditionalinBackground(String title) {

        for (int i = 0; i < 20; i++) {

            System.out.println("importing in background " + title);

            try {
                Thread.sleep(5000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("I'm done!!!! importing in background after 5000 " + title);
        }


    }
}
