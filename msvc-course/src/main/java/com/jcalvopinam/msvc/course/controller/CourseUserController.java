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

import com.jcalvopinam.msvc.course.dto.UserDTO;
import com.jcalvopinam.msvc.course.service.CourseUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jcalvopinam <juan.calvopina@gmail.com>
 */
@RestController
@RequestMapping(value = "/assignments")
public class CourseUserController {

    private CourseUserService courseUserService;

    public CourseUserController(CourseUserService courseUserService) {
        this.courseUserService = courseUserService;
    }

    @PutMapping(value = "/{courseId}")
    public ResponseEntity<UserDTO> assignCourseUser(final @RequestBody UserDTO userDTO, final BindingResult result,
                                                    @PathVariable Long courseId) {
        return ResponseEntity.status(HttpStatus.OK)
                             .body(courseUserService.assignUserToCourse(userDTO, result, courseId));
    }

    @PostMapping(value = "/{courseId}")
    public ResponseEntity<UserDTO> createUser(final @RequestBody UserDTO userDTO, final BindingResult result,
                                              @PathVariable Long courseId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(courseUserService.createUser(userDTO, result, courseId));
    }

    @DeleteMapping(value = "/{courseId}")
    public ResponseEntity<UserDTO> deleteUserFromCourse(final @RequestBody UserDTO userDTO, final BindingResult result,
                                                        @PathVariable Long courseId) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                             .body(courseUserService.deleteUserFromCourse(userDTO, result, courseId));
    }

    @DeleteMapping(value = "/users/{userId}")
    public ResponseEntity<Void> unassignCourseUserById(final @PathVariable Long userId) {
        courseUserService.unassignCourseUserById(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                             .build();
    }

}
