package com.javabrains.springboot.tutorial.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    private List<Topic> course = new ArrayList<>(Arrays.asList(
            new Topic("spring", "Spring Framework", "Spring Framework Description"),
            new Topic("java", "Core Java", "Core Java Description"),
            new Topic("javascript", "JavaScript", "JavaScript Description")
    ));

    public void loadTopics() {
        topicRepository.saveAll(Arrays.asList(
                new Topic("spring", "Spring Framework", "Spring Framework Description"),
                new Topic("java", "Core Java", "Core Java Description"),
                new Topic("javascript", "JavaScript", "JavaScript Description")
        ));
    }

    public List<Topic> getAllTopics() {
        List<Topic> topicList = new ArrayList<>();
        topicRepository.findAll().forEach(topicList::add);

        return topicList;
    }

    public Topic getTopic(String id) {
        return topicRepository.findById(id).get();
    }

    public void addTopic(Topic topic) {
        topicRepository.save(topic);
    }

    public void updateTopic(String id, Topic topic) {
        Optional<Topic> t = topicRepository.findById(id);

        if (t.isPresent()) {
            topicRepository.delete(t.get());
            topicRepository.save(topic);
        }
    }

    public void deleteTopic(String id) {
        topicRepository.deleteById(id);
    }
}
