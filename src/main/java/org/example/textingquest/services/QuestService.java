package org.example.textingquest.services;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.textingquest.daos.QuestDAO;
import org.example.textingquest.dtos.QuestDTO;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QuestService {
    
    private static final QuestService INSTANCE=new QuestService();

    private final QuestDAO questDAO=QuestDAO.getInstance();

    public static QuestService getInstance() {
        return INSTANCE;
    }

    public Optional findById(Integer id) {
        // Вызываем метод DAO и возвращаем результат
        return questDAO.findById(id);
    }
}
