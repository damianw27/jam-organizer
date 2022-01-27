package pl.wilenskid.jamorganizer.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.wilenskid.jamorganizer.builder.RedirectionPath;
import pl.wilenskid.jamorganizer.entity.bean.ReportReasonCreateBean;
import pl.wilenskid.jamorganizer.service.ReportReasonService;

import javax.inject.Named;

@Controller
@RequestMapping("/report-reason")
public class ReportReasonRest {

    private final ReportReasonService reportReasonService;

    @Named
    public ReportReasonRest(ReportReasonService reportReasonService) {
        this.reportReasonService = reportReasonService;
    }

    @PostMapping
    public String createReportReason(ReportReasonCreateBean reportReasonCreateBean) {
        reportReasonService.createReportReason(reportReasonCreateBean);
        return RedirectionPath
            .builder()
            .basePath("/admin/panel")
            .withParam("focusReportReason", "true")
            .build();
    }

    @DeleteMapping("/{reportReasonId}")
    public String deleteReportReason(@PathVariable Long reportReasonId) {
        reportReasonService.deleteReportReason(reportReasonId);
        return RedirectionPath
            .builder()
            .basePath("/admin/panel")
            .build();
    }
}
