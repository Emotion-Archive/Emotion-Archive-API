package com.emotion.archive.constants;

import com.emotion.archive.model.dto.ResultDTO;
import com.emotion.archive.utils.GsonUtils;
import org.slf4j.Logger;

public class ReturnValue {

    public static String errReturn(Logger logger, Long id, String errMsg) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setResultYn(ConstValue.RESULT_N);
        resultDTO.setId(id);
        resultDTO.setErrMsg(errMsg);

        String result = GsonUtils.convertToJsonStr(resultDTO);
        logger.error("errReturn : " + result);
        return result;
    }

    public static String successReturn(Logger logger, Long id) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setResultYn(ConstValue.RESULT_Y);
        resultDTO.setId(id);

        String result = GsonUtils.convertToJsonStr(resultDTO);
        logger.info("successReturn : " + result);
        return result;
    }

}
