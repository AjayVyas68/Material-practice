package com.unitechApi.MachineSetParameter.service;

import com.unitechApi.MachineSetParameter.model.Drawframesperkg;
import com.unitechApi.MachineSetParameter.repository.DrawFramesRepository;
import com.unitechApi.exception.ExceptionService.DateMisMatchException;
import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DrawframesService {
    public static final float CONSTANT = (float) 0.2835;
    private final DrawFramesRepository drawFramesRepository;
    DecimalFormat df = new DecimalFormat("#.###");

    public DrawframesService(DrawFramesRepository drawFramesRepository) {
        this.drawFramesRepository = drawFramesRepository;
    }

    public Drawframesperkg SaveData(Drawframesperkg drawframesperkg) {
        df.setMaximumFractionDigits(3);
        drawframesperkg.setProductiononratekgdfper8hour(Float.parseFloat(df.format(((CONSTANT * drawframesperkg.getDeliveryspeed() * drawframesperkg.getMachineefficency()) / (drawframesperkg.getSilverhank() * 100)) * 2)));
        System.out.println(drawframesperkg.getProductiononratekgdfper8hour());
        drawframesperkg.setMachineefficencykgdfper6hours(Float.parseFloat(df.format(drawframesperkg.getProductiononratekgdfper8hour() * 6 / 8)));
        drawframesperkg.setMachineefficencykgdfperday(Float.parseFloat(df.format(drawframesperkg.getProductiononratekgdfper8hour() * 24 / 8)));
        log.info("{ } DrawFrames Data In KG ", drawframesperkg);
        return drawFramesRepository.save(drawframesperkg);
    }

    public Object ViewData() {
        return drawFramesRepository.findAll();

    }

    public void DeleteReading(Long id) throws ResourceNotFound {
        drawFramesRepository.deleteById(id);
    }

    public Optional<Drawframesperkg> FindByData(Long id) {
        return Optional.ofNullable(drawFramesRepository.findById(id).orElseThrow(() -> new ResourceNotFound("can't find data")));
    }

    public List<Drawframesperkg> FindByDate(Date start, Date end) {
        java.util.Date date = new java.util.Date();

        if (date.before(start)) {
            throw new DateMisMatchException(" you can not enter -> " + date + "  -> " + start);
        } else if (date.before(end)) {
            throw new DateMisMatchException(" you can not enter -> " + date + "  -> " + end);
        }
        return drawFramesRepository.findByCreatedAtBetween(start, end)
                .stream()
                .sorted(Comparator.comparing(o->o.getDrawFramesMachine().getId()))
                .collect(Collectors.toList());
    }

    public List<Drawframesperkg> FindBySingleDate(Date start) {
        return drawFramesRepository.findByCreatedAt(start)
                .stream()
                .sorted(Comparator.comparing(o->o.getDrawFramesMachine().getId()))
                .collect(Collectors.toList());
    }

    public List<Drawframesperkg> ExcelDateToDateReport(Date start, Date end) {
        return drawFramesRepository.findByShiftdateBetween(start, end)
                .stream()
                .sorted(Comparator.comparing(o->o.getDrawFramesMachine().getId()))
                .collect(Collectors.toList());
    }

    public List<Drawframesperkg> ExcelDateToPerDateReport(Date start) {
        return drawFramesRepository.findByShiftdate(start)
                .stream()
                .sorted(Comparator.comparing(o->o.getDrawFramesMachine().getId()))
                .collect(Collectors.toList());
    }
}
