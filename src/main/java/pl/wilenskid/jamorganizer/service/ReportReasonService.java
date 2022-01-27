package pl.wilenskid.jamorganizer.service;

import pl.wilenskid.jamorganizer.entity.ReportReason;
import pl.wilenskid.jamorganizer.entity.bean.ReportReasonBean;
import pl.wilenskid.jamorganizer.entity.bean.ReportReasonCreateBean;
import pl.wilenskid.jamorganizer.repository.ReportReasonRepository;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Named
public class ReportReasonService {

    private final ReportReasonRepository reportReasonRepository;

    @Inject
    public ReportReasonService(ReportReasonRepository reportReasonRepository) {
        this.reportReasonRepository = reportReasonRepository;
    }

    public Stream<ReportReason> getAllReportReasons() {
        return StreamSupport.stream(reportReasonRepository.findAll().spliterator(), false);
    }

    public ReportReason createReportReason(ReportReasonCreateBean reportReasonCreateBean) {
        if (reportReasonCreateBean.getLabel() == null || reportReasonCreateBean.getDescription() == null) {
            return null;
        }

        ReportReason reportReason = new ReportReason();
        reportReason.setLabel(reportReasonCreateBean.getLabel());
        reportReason.setDescription(reportReasonCreateBean.getDescription());
        reportReasonRepository.save(reportReason);
        return reportReason;
    }

    public void deleteReportReason(long reportReasonId) {
        reportReasonRepository
            .findById(reportReasonId)
            .ifPresent(reportReasonRepository::delete);
    }

    public ReportReasonBean toBean(ReportReason reportReason) {
        return ReportReasonBean
            .builder()
            .id(reportReason.getId())
            .label(reportReason.getLabel())
            .description(reportReason.getDescription())
            .build();
    }

}
