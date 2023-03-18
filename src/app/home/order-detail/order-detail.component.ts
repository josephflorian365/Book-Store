import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HomeService } from '../shared/home.service';
import { SalesItem, SalesOrder } from '../shared/sales-order.model';

@Component({
  selector: 'app-order-detail',
  templateUrl: './order-detail.component.html'
})
export class OrderDetailComponent implements OnInit{

  salesOrder?: SalesOrder;

  constructor(
    private homeService: HomeService,
    private route: ActivatedRoute
  ){}

  ngOnInit(): void {
    const orderId = parseInt(this.route.snapshot.paramMap.get('id')!);

    this.homeService.getOrder(orderId)
    .subscribe(salesOrder => {
      this.salesOrder = salesOrder;
    })
  }

  downloadBook(item: SalesItem){
    this.homeService.downloadBookFromSalesItem(this.salesOrder!.id, item.id)
    .subscribe(blob => {
      const _blob = new Blob([blob], {
        type: 'application/pdf; chartset=utf8-8'
      });

      const a = document.createElement('a');
      const url = window.URL.createObjectURL(_blob);

      a.href = url;
      a.download = `${item.book.title}.pdf`;
      a.click();
      URL.revokeObjectURL(url);

      item.downloadsAvailable -= 1;
    })
  }
}
