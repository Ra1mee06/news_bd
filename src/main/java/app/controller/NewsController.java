package app.controller;

import app.entity.News;
import app.repository.NewsRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsController {

    private final NewsRepository newsRepository;

    public NewsController(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @GetMapping
    public List<News> getAll() {
        return newsRepository.findAll();
    }

    @PostMapping
    public News create(@RequestBody News news) {
        return newsRepository.save(news);
    }
}
