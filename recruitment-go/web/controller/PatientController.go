package controller

import (
	"github.com/gin-gonic/gin"
	"net/http"
	"recruitment/biz/dao"
	"recruitment/biz/service"
	"recruitment/common"
	"sync"
)

type PatientController struct {
	patientService *service.PatientService
}

var patientController *PatientController
var patientControllerLock sync.Mutex

func GetPatientController() *PatientController {
	if patientController != nil {
		return patientController
	}
	patientControllerLock.Lock()
	defer patientControllerLock.Unlock()
	if patientController != nil {
		return patientController
	}
	patientController = &PatientController{}
	patientController.patientService = service.GetPatientService()
	return patientController
}

func (this *PatientController) ListPatientPage(context *gin.Context) {
	var query dao.PatientQuery
	_ = context.ShouldBind(&query)
	patientRes, paginator := this.patientService.ListPatientPage(&query)
	pageResult := common.PageResult{Paginator: paginator, Data: patientRes}
	result := common.Result(pageResult)
	context.JSON(http.StatusOK, result)
}
