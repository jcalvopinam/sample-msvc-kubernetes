/*
 * MIT License
 *
 * Copyright (c) 2023 JUAN CALVOPINA M
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package com.jcalvopinam.msvc.course.controller;

import com.jcalvopinam.msvc.course.domain.Course;
import com.jcalvopinam.msvc.course.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author jcalvopinam <juan.calvopina@gmail.com>
 */
@RestController
@RequestMapping(value = "/")
public class CourseController {

    private CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping()
    public ResponseEntity<List<Course>> findAll() {
        return ResponseEntity.status(HttpStatus.OK)
                             .body(courseService.getCourses());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Course> findById(@PathVariable final Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                             .body(courseService.getCoursesWithUsers(id));
    }

    @PostMapping
    public ResponseEntity<Course> save(@Valid @RequestBody final Course course, final BindingResult result) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(courseService.save(course, result));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Course> update(@Valid @RequestBody final Course course, final BindingResult result,
                                         @PathVariable final Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                             .body(courseService.update(course, result, id));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        courseService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                             .build();
    }

}
