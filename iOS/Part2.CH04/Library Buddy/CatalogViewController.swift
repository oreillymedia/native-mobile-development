//
//  CatalogViewController.swift
//  Library Buddy
//
//  Created by Shaun Lewis on 6/28/19.
//  Copyright © 2019 Shaun Lewis. All rights reserved.
//

import UIKit

class CatalogViewController: UIViewController {

    @IBOutlet weak var tableView: UITableView!
    lazy var dataSource: ListDataSource = {
        return ListDataSource()
    }()

    override func viewDidLoad() {
        super.viewDidLoad()
        tableView.dataSource = dataSource
    }
}
