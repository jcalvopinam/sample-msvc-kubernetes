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

package com.jcalvopinam.msvc.course.service;

import com.jcalvopinam.msvc.course.domain.Course;
import com.jcalvopinam.msvc.course.repository.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author jcalvopinam <juan.calvopina@gmail.com>
 */
@Service
public class CourseServiceImpl implements CourseService {

    private CourseRepository courseRepository;

    public CourseServiceImpl(final CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Course> getCourses() {
        return (List<Course>) courseRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Course getCourseById(final Long id) {
        return findById(id);
    }

    @Override
    @Transactional
    public Course save(final Course course) {
        return courseRepository.save(course);
    }

    @Override
    @Transactional
    public Course update(final Long id, final Course course) {
        final Course currentCourse = findById(id);
        currentCourse.setName(course.getName());
        return courseRepository.save(currentCourse);
    }

    @Override
    @Transactional
    public void delete(final Long id) {
        final Course currentCourse = findById(id);
        courseRepository.delete(currentCourse);
    }

    private Course findById(final Long id) {
        return courseRepository.findById(id)
                               .orElseThrow(() -> new RuntimeException("Course not found"));
    }

}