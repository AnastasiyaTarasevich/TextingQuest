package org.example.textingquest.services;
import org.example.textingquest.entities.Chapter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.textingquest.daos.ChapterDAO;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChapterService {

    private static final ChapterService INSTANCE=new ChapterService();

    public static ChapterService getInstance() {
        return INSTANCE;
    }
    private final ChapterDAO chapterDAO = ChapterDAO.getInstance();


}

