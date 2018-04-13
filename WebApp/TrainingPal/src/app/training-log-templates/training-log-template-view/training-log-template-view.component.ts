import { Component, OnInit } from '@angular/core';
import {TrainingLogTemplate} from '../../_models/training-log-template.model';
import {TrainingLogTemplateService} from '../../_services/training-log-template.service';
import {AlertService} from '../../_services/alert.service';
import {ActivatedRoute, Router} from '@angular/router';
import {AuthService} from '../../_services/auth.service';
import {ErrorResponse} from '../../_models/response/error-response.model';
import {BaseResponse} from '../../_models/response/base-response.model';
import {TrainingLogTemplateResponse} from '../../_models/response/training-log-template-response.model';

@Component({
  selector: 'app-training-log-template-view',
  templateUrl: './training-log-template-view.component.html',
  styleUrls: ['./training-log-template-view.component.css']
})
export class TrainingLogTemplateViewComponent implements OnInit {

  template: TrainingLogTemplate;

  constructor(
    private templateService: TrainingLogTemplateService,
    private authService: AuthService,
    private route: ActivatedRoute,
    private router: Router,
    private alertService: AlertService
  ) { }

  ngOnInit() {
    this.templateService.getTemplate(this.route.snapshot.params['template_id'], this.authService.getCurrentAuthObject().UserId).subscribe(
      (response: TrainingLogTemplateResponse) => {
        this.template = response.Data.Template;
      },
      (error: ErrorResponse) => {
        console.error(error.error.Message);
      }
    );
  }

  onEditClicked() {
    this.router.navigate([this.router.url, 'edit']);
  }

  onDeleteClicked() {
    if (confirm('Are you sure you wish to delete this template?')) {
      this.templateService.deleteTemplate(this.route.snapshot.params['template_id'], this.authService.getCurrentAuthObject().UserId).subscribe(
        (response: BaseResponse) => {
          this.alertService.success(response.Message, true);
          this.router.navigate(['templates']);
        },
        (error: ErrorResponse) => {
          this.alertService.error(error.error.Message);
        }
      );
    }
  }

}
