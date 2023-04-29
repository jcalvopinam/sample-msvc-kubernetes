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
import com.jcalvopinam.msvc.course.exception.ClientException;
import feign.FeignException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

/**
 * @author jcalvopinam <juan.calvopina@gmail.com>
 */
@Service
public class CourseUserServiceImpl implements CourseUserService {

    private CourseService courseService;
    private UserClientRest client;

    public CourseUserServiceImpl(final UserClientRest client, final CourseService courseService) {
        this.client = client;
        this.courseService = courseService;
    }

    @Override
    @Transactional
    public UserDTO assignUserToCourse(final UserDTO user, final BindingResult result, final Long courseId) {
        UserDTO clientUser;
        try {
            final Course course = courseService.getCourseById(courseId);
            clientUser = client.findUserById(user.getId());
            final CourseUser courseUser = new CourseUser();
            courseUser.setUserId(clientUser.getId());
            course.addCourseUser(courseUser);
            courseService.save(course, result);
        } catch (FeignException exception) {
            throw new ClientException(exception.getMessage());
        }
        return clientUser;
    }

    @Override
    @Transactional
    public UserDTO createUser(final UserDTO user, final BindingResult result, final Long courseId) {
        UserDTO clientUser;
        try {
            final Course course = courseService.getCourseById(courseId);
            clientUser = client.createUser(user);
            final CourseUser courseUser = new CourseUser();
            courseUser.setUserId(clientUser.getId());
            course.addCourseUser(courseUser);
            courseService.save(course, result);
        } catch (FeignException exception) {
            throw new ClientException(exception.getMessage());
        }
        return clientUser;
    }

    @Override
    @Transactional
    public UserDTO deleteUserFromCourse(final UserDTO user, final BindingResult result, final Long courseId) {
        UserDTO clientUser;
        try {
            final Course course = courseService.getCourseById(courseId);
            clientUser = client.findUserById(user.getId());
            final CourseUser courseUser = new CourseUser();
            courseUser.setUserId(clientUser.getId());
            course.removeCourseUser(courseUser);
            courseService.save(course, result);
        } catch (FeignException exception) {
            throw new ClientException(exception.getMessage());
        }
        return clientUser;
    }

}
