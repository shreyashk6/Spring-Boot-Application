package com.shrey.reviewms.review;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews(@RequestParam Long companyId) {
        return ResponseEntity.ok(reviewService.getAllReviews(companyId));
    }

    @PostMapping
    public ResponseEntity<String> addReview(@RequestParam Long companyId,
                                            @RequestBody Review review) {
        return reviewService.addReview(companyId, review)
                ? ResponseEntity.status(HttpStatus.CREATED).body("Review Added Successfully")
                : ResponseEntity.badRequest().body("Invalid request");
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReview(@PathVariable Long reviewId) {

        return new ResponseEntity<>(reviewService.getReview((reviewId)),HttpStatus.OK);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<String> updateReview(@PathVariable Long reviewId,
                                               @RequestBody Review review) {
        boolean updated = reviewService.updateReview(reviewId, review);
        return updated ? ResponseEntity.ok("Review Updated Successfully")
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(
            @PathVariable Long reviewId,
            @RequestParam(required = false) Long companyId) {

        Review review = reviewService.getReview(reviewId);

        if (review == null) {
            return ResponseEntity.notFound().build();
        }

        if (companyId != null && !review.getCompanyId().equals(companyId)) {
            return ResponseEntity.notFound().build();
        }

        boolean deleted = reviewService.deleteReview(reviewId);

        return deleted
                ? ResponseEntity.ok("Review has been Deleted")
                : ResponseEntity.notFound().build();
    }

}