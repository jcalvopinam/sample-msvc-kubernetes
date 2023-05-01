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

import com.jcalvopinam.msvc.course.client.UserClientRest;
import com.jcalvopinam.msvc.course.domain.Course;
import com.jcalvopinam.msvc.course.domain.CourseUser;
import com.jcalvopinam.msvc.course.dto.UserDTO;
import com.jcalvopinam.msvc.course.exception.BadRequestException;
import com.jcalvopinam.msvc.course.exception.NotFoundException;
import com.jcalvopinam.msvc.course.repository.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jcalvopinam <juan.calvopina@gmail.com>
 */
@Service
public class CourseServiceImpl implements CourseService {

    public static final String COURSE_NOT_FOUND = "Course not found";
    private final CourseRepository courseRepository;
    private final UserClientRest client;

    public CourseServiceImpl(final CourseRepository courseRepository, final UserClientRest client) {
        this.courseRepository = courseRepository;
        this.client = client;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Course> getCourses() {
        return (List<Course>) courseRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Course getCoursesWithUsers(final Long id) {
        final Course course = findById(id);
        if (course.getCourseUsers()
                  .isEmpty()) {
            throw new NotFoundException(COURSE_NOT_FOUND);
        }
        List<Long> userIds = course.getCourseUsers()
                                   .stream()
                                   .map(CourseUser::getUserId)
                                   .toList();
        final List<UserDTO> userDTOList = client.findAllUsersById(userIds);
        course.setUserDTOList(userDTOList);
        return course;
    }

    @Override
    @Transactional(readOnly = true)
    public Course getCourseById(final Long id) {
        return findById(id);
    }

    @Override
    @Transactional
    public Course save(final Course course, final BindingResult result) {
        validateRequest(result);
        return courseRepository.save(course);
    }

    @Override
    @Transactional
    public Course update(final Course course, final BindingResult result, final Long id) {
        validateRequest(result);
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
                               .orElseThrow(() -> new NotFoundException(COURSE_NOT_FOUND));
    }

    private void validateRequest(final BindingResult result) {
        final Map<String, String> errors = new HashMap<>();
        final List<FieldError> fieldErrors = result.getFieldErrors();

        if (!fieldErrors.isEmpty()) {
            fieldErrors.forEach(error -> {
                final String message = "The field " + error.getField() + " " + error.getDefaultMessage();
                errors.put(error.getField(), message);
            });
            throw new BadRequestException("Please check the following issues", errors);
        }
    }

}
