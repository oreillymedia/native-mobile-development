//
//  WelcomeViewController.swift
//  Library Buddy
//
//  Created by Shaun Lewis on 6/21/19.
//  Copyright © 2019 Shaun Lewis. All rights reserved.
//

import UIKit

class WelcomeViewController: UIViewController {
    @IBOutlet weak var headerLabel: UILabel!

    override func viewDidLoad() {
        super.viewDidLoad()
        headerLabel.textColor = .red
    }
}
